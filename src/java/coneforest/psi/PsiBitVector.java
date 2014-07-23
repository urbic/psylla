package coneforest.psi;

public class PsiBitVector
	extends PsiObject
	implements PsiArraylike<PsiBoolean>, PsiLogical<PsiBitVector>
{
	public PsiBitVector()
	{
		this(new java.util.BitSet());
	}

	public PsiBitVector(java.util.BitSet bitvector)
	{
		this.bitvector=bitvector;
	}

	public String getTypeName()
	{
		return "bitvector";
	}
	
	public String toString()
	{
		return "-bitvector-";
	}

	private java.util.BitSet getBitVector()
	{
		return bitvector;
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

	public PsiBitVector not()
	{
		java.util.BitSet result=(java.util.BitSet)bitvector.clone();
		result.flip(0, result.size());
		return new PsiBitVector(result);
	}

	public PsiBitVector or(final PsiBitVector obj)
	{
		java.util.BitSet result=(java.util.BitSet)bitvector.clone();
		result.or(obj.getBitVector());
		return new PsiBitVector(result);
	}

	public PsiBitVector and(final PsiBitVector obj)
	{
		java.util.BitSet result=(java.util.BitSet)bitvector.clone();
		result.and(obj.getBitVector());
		return new PsiBitVector(result);
	}

	public PsiBitVector xor(final PsiBitVector obj)
	{
		java.util.BitSet result=(java.util.BitSet)bitvector.clone();
		result.xor(obj.getBitVector());
		return new PsiBitVector(result);
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

	public int length()
	{
		return bitvector.size();
	}

	private java.util.BitSet bitvector;
}
