package coneforest.psi;

public class PsiBitSet
	extends PsiObject
	implements PsiSetlike<PsiInteger>
{
	public String getTypeName()
	{
		return "bitset";
	}

	public java.util.BitSet getBitSet()
	{
		return bitset;
	}
	
	public String toString()
	{
		return "-bitset-";
	}

	public void append(PsiInteger index)
		throws PsiException
	{
		try
		{
			bitset.set(index.getValue().intValue(), true);
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiException("rangecheck");
		}
	}

	public void appendAll(PsiSetlike<PsiInteger> setlike)
		throws PsiException
	{
		if(setlike instanceof PsiBitSet)
			bitset.or(((PsiBitSet)setlike).getBitSet());
		else
			for(PsiInteger integer: setlike)
				append(integer);
	}

	public void remove(PsiInteger integer)
		throws PsiException
	{
		try
		{
			bitset.set(integer.getValue().intValue(), false);
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiException("rangecheck");
		}
	}

	public void removeAll(PsiSetlike<PsiInteger> setlike)
		throws PsiException
	{
		if(setlike instanceof PsiBitSet)
			bitset.andNot(((PsiBitSet)setlike).getBitSet());
		else
			for(PsiInteger integer: setlike)
				remove(integer);
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
					if(hasNext())
					{
						PsiInteger result=new PsiInteger(index);
						index=bitset.nextSetBit(index+1);
						return result;
					}
					else
						throw new java.util.NoSuchElementException();
				}

				public void remove()
				{
					throw new UnsupportedOperationException();
				}

				private int index=bitset.nextSetBit(0);
			};
	}

	public PsiInteger length()
	{
		return new PsiInteger(bitset.cardinality());
	}

	public PsiBoolean isEmpty()
	{
		return new PsiBoolean(bitset.isEmpty());
	}

	public PsiBoolean eq(final PsiObject obj)
	{
		return new PsiBoolean(obj instanceof PsiBitSet
				&& bitset.equals(((PsiBitSet)obj).getBitSet()));
	}

	private java.util.BitSet bitset=new java.util.BitSet();
}
