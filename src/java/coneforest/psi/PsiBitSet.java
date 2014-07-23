package coneforest.psi;

public class PsiBitSet
	extends PsiObject
	implements Iterable<PsiInteger>, PsiComposite<PsiInteger>
{
	public String getTypeName()
	{
		return "bitset";
	}
	
	public String toString()
	{
		return "-bitset-";
	}

	public void append(int index)
		throws PsiException
	{
		try
		{
			bitset.set(index, true);
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiException("rangecheck");
		}
	}

	public void append(PsiInteger oIndex)
		throws PsiException
	{
		append(oIndex.getValue().intValue());
	}

	public void remove(int index)
		throws PsiException
	{
		try
		{
			bitset.set(index, false);
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiException("rangecheck");
		}
	}

	public void remove(PsiInteger oIndex)
		throws PsiException
	{
		remove(oIndex.getValue().intValue());
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

	public int length()
	{
		return bitset.cardinality();
	}

	private java.util.BitSet bitset=new java.util.BitSet();
}
