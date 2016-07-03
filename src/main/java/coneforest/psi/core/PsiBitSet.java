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
	public PsiBitSet(final java.util.BitSet bitset)
	{
		this.bitset=bitset;
	}

	/**
	*	@return a string {@code "bitset"}.
	*/
	@Override
	public String typeName()
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
		final StringBuilder sb=new StringBuilder("-bitset:");
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
	public void psiAppend(final PsiInteger oIndex)
		throws PsiException
	{
		try
		{
			bitset.set(oIndex.intValue(), true);
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiRangeCheckException();
		}
	}

	@Override
	public void psiAppendAll(final PsiIterable<? extends PsiInteger> oIterable)
		throws PsiException
	{
		if(oIterable instanceof PsiBitSet)
			bitset.or(((PsiBitSet)oIterable).bitset);
		else
			PsiSetlike.super.psiAppendAll(oIterable);
	}

	@Override
	public void psiRemove(final PsiInteger oIndex)
	{
		try
		{
			bitset.set(oIndex.intValue(), false);
		}
		catch(IndexOutOfBoundsException e)
		{
		}
	}

	@Override
	public void psiRemoveAll(PsiIterable<? extends PsiInteger> oIterable)
	{
		if(oIterable instanceof PsiBitSet)
			bitset.andNot(((PsiBitSet)oIterable).bitset);
		else
			PsiSetlike.super.psiRemoveAll(oIterable);
	}

	@Override
	public java.util.Iterator<PsiInteger> iterator()
	{
		return new java.util.Iterator<PsiInteger>()
			{
				@Override
				public boolean hasNext()
				{
					return index>=0;
				}

				@Override
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
	public PsiBoolean psiContains(final PsiInteger oElement)
	{
		return PsiBoolean.valueOf(bitset.get(oElement.intValue()));
	}

	@Override
	public PsiBoolean psiIntersects(final PsiSetlike oSet)
	{
		if(oSet instanceof PsiBitSet)
			return PsiBoolean.valueOf(bitset.intersects(((PsiBitSet)oSet).bitset));
		else
			return PsiSetlike.super.psiIntersects(oSet);
	}

	@Override
	public PsiBoolean psiEq(final PsiObject o)
	{
		return PsiBoolean.valueOf(o instanceof PsiBitSet
				&& bitset.equals(((PsiBitSet)o).bitset));
	}

	private final java.util.BitSet bitset;
}
