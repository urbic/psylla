package coneforest.psi;

public class PsiBitVector extends PsiObject implements Iterable<PsiBoolean>
{
	public String getTypeName()
	{
		return "bitvector";
	}
	
	public String toString()
	{
		return "-bitvector-";
	}

	public boolean get(int index)
		throws PsiException
	{
		try
		{
			return bitvector.get(index);
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
			bitvector.set(index, value);
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
					return index<bitvector.length();
				}

				public PsiBoolean next()
				{
					if(hasNext())
						return new PsiBoolean(bitvector.get(index++));
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
		return bitvector.length();
	}

	private java.util.BitSet bitvector=new java.util.BitSet();
}
