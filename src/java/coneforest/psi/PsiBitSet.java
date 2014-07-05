package coneforest.psi;

public class PsiBitSet extends PsiObject implements Iterable<PsiBoolean>
{
	public String getTypeName()
	{
		return "bitset";
	}
	
	public String toString()
	{
		return "-bitset-";
	}

	public boolean get(int index)
		throws PsiException
	{
		try
		{
			return bitset.get(index);
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiException("rangecheck");
		}
	}

	public PsiBoolean get(PsiInteger oIndex)
		throws PsiException
	{
		return new PsiBoolean(get(oIndex.getValue().intValue()));
	}

	public void put(int index, boolean value)
		throws PsiException
	{
		try
		{
			bitset.set(index, value);
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiException("rangecheck");
		}
	}

	public void put(PsiInteger oIndex, PsiBoolean oValue)
		throws PsiException
	{
		put(oIndex.getValue().intValue(), oValue.getValue());
	}

	public java.util.Iterator<PsiBoolean> iterator()
	{
		return new java.util.Iterator<PsiBoolean>()
			{
				public boolean hasNext()
				{
					return index<bitset.length();
				}

				public PsiBoolean next()
				{
					if(hasNext())
						return new PsiBoolean(bitset.get(index++));
					else
						throw new java.util.NoSuchElementException();
				}

				public void remove()
				{
					throw new UnsupportedOperationException();
				}

				private int index=0;
			};
	}

	public int size()
	{
		return bitset.length();
	}

	private java.util.BitSet bitset=new java.util.BitSet();
}
