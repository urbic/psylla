package coneforest.psi.core;

/**
 *	A representation of Ψ-{@code bitset}, a set of nonnegative Ψ-{@code
 *	integer} objects.
 */
public class PsiBitSet
	implements PsiSetlike<PsiInteger>
{
	/**
	*	Instantiate an empty Ψ-{@code bitset} object.
	*/
	public PsiBitSet()
	{
		this(new java.util.BitSet());
	}

	/**
	*	Instantiate a Ψ-{@code bitset} object from a given {@link
	*	java.util.BitSet} object.
	*
	*	@param bitset a bit set.
	*/
	public PsiBitSet(java.util.BitSet bitset)
	{
		this.bitset=bitset;
	}

	/**
	*	@return a string {@code "bitset"}.
	*/
	@Override
	public String getTypeName()
	{
		return "bitset";
	}

	@Override
	public PsiBitSet psiClone()
	{
		return new PsiBitSet((java.util.BitSet)bitset.clone());
	}

	@Override
	public String toSyntaxString()
	{
		StringBuilder sb=new StringBuilder("-bitset:");
		int j=-1;
		for(int i=bitset.nextSetBit(0); i>=0; i=bitset.nextSetBit(i+1))
		{
			for(int k=j+1; k<i; k++)
				sb.append('0');
			sb.append('1');
			j=i;
		}
		sb.append('-');
		return sb.toString();
	}

	public java.util.BitSet getBitSet()
	{
		return bitset;
	}

	@Override
	public void psiAppend(PsiInteger index)
		throws PsiException
	{
		try
		{
			bitset.set(index.intValue(), true);
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiRangeCheckException();
		}
	}

	@Override
	public void psiAppendAll(PsiIterable<? extends PsiInteger> iterable)
		throws PsiException
	{
		if(iterable instanceof PsiBitSet)
			bitset.or(((PsiBitSet)iterable).bitset);
		else
			PsiSetlike.super.psiAppendAll(iterable);
	}

	@Override
	public void psiRemove(PsiInteger integer)
	{
		try
		{
			bitset.set(integer.intValue(), false);
		}
		catch(IndexOutOfBoundsException e)
		{
		}
	}

	@Override
	public void psiRemoveAll(PsiIterable<? extends PsiInteger> iterable)
	{
		if(iterable instanceof PsiBitSet)
			bitset.andNot(((PsiBitSet)iterable).bitset);
		else
			PsiSetlike.super.psiRemoveAll(iterable);
	}

	public java.util.Iterator<PsiInteger> iterator()
	{
		return new java.util.Iterator<PsiInteger>()
			{
				public boolean hasNext()
				{
					return index>=0;
				}

				public PsiInteger next()
				{
					//if(hasNext())
					//{
						PsiInteger result=PsiInteger.valueOf(index);
						index=bitset.nextSetBit(index+1);
						return result;
					//}
					//else
					//	throw new java.util.NoSuchElementException();
				}

				private int index=bitset.nextSetBit(0);
			};
	}

	@Override
	public void psiClear()
	{
		bitset.clear();
	}

	@Override
	public int length()
	{
		return bitset.cardinality();
	}

	@Override
	public PsiBoolean psiContains(PsiInteger oElement)
	{
		return PsiBoolean.valueOf(bitset.get(oElement.intValue()));
	}

	@Override
	public PsiBoolean psiIntersects(PsiSetlike setlike)
	{
		if(setlike instanceof PsiBitSet)
			return PsiBoolean.valueOf(bitset.intersects(((PsiBitSet)setlike).bitset));
		else
			return PsiSetlike.super.psiIntersects(setlike);
	}

	@Override
	public PsiBoolean psiEq(final PsiObject obj)
	{
		return PsiBoolean.valueOf(obj instanceof PsiBitSet
				&& bitset.equals(((PsiBitSet)obj).bitset));
	}

	private final java.util.BitSet bitset;
}
