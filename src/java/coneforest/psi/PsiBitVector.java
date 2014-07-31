package coneforest.psi;

public class PsiBitVector
	extends PsiAbstractArray<PsiBoolean>
	implements
		PsiLogical<PsiBitVector>
{
	public PsiBitVector()
	{
		this(new java.util.BitSet());
	}

	public PsiBitVector(java.util.BitSet bitvector)
	{
		this.bitvector=bitvector;
	}

	@Override
	public String getTypeName()
	{
		return "bitvector";
	}

	private java.util.BitSet getBitVector()
	{
		return bitvector;
	}

	@Override
	public PsiBoolean get(int index)
		throws PsiException
	{
		if(index>=size)
			throw new PsiException("rangecheck");
		try
		{
			return new PsiBoolean(bitvector.get(index));
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiException("rangecheck");
		}
	}

	/*
	@Override
	public PsiBoolean psiGet(PsiInteger index)
		throws PsiException
	{
		int indexValue=index.getValue().intValue();
		if(indexValue>=size)
			throw new PsiException("rangecheck");
		try
		{
			return new PsiBoolean(bitvector.get(indexValue));
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiException("rangecheck");
		}
	}
	*/

	@Override
	public void put(int index, PsiBoolean bool)
		throws PsiException
	{
		if(index>=size)
			throw new PsiException("rangecheck");
		try
		{
			bitvector.set(index, bool.getValue());
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiException("rangecheck");
		}
	}


	/*
	@Override
	public void psiPut(PsiInteger index, PsiBoolean bool)
		throws PsiException
	{
		int indexValue=index.getValue().intValue();
		if(indexValue>=size)
			throw new PsiException("rangecheck");
		try
		{
			bitvector.set(indexValue, bool.getValue());
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiException("rangecheck");
		}
	}
	*/

	@Override
	public void psiAppend(PsiBoolean bool)
	{
		bitvector.set(size++, bool.getValue());
	}

	@Override
	public void psiInsert(PsiInteger index, PsiBoolean bool)
	{
		int indexValue=index.getValue().intValue();
		size++;
		for(int i=size-1; i>indexValue; i--)
			bitvector.set(i, bitvector.get(i-1));
		bitvector.set(indexValue, bool.getValue());
	}

	/*
	@Override
	public void psiInsertAll(PsiInteger index, PsiIterable<? extends PsiBoolean> iterable)
		throws PsiException
	{
		throw new PsiException("TODO");
	}
	*/

	@Override
	public PsiBitVector psiNot()
	{
		java.util.BitSet result=(java.util.BitSet)bitvector.clone();
		result.flip(0, result.size());
		return new PsiBitVector(result);
	}

	@Override
	public PsiBitVector psiOr(final PsiBitVector obj)
	{
		java.util.BitSet result=(java.util.BitSet)bitvector.clone();
		result.or(obj.getBitVector());
		return new PsiBitVector(result);
	}

	@Override
	public PsiBitVector psiAnd(final PsiBitVector obj)
	{
		java.util.BitSet result=(java.util.BitSet)bitvector.clone();
		result.and(obj.getBitVector());
		return new PsiBitVector(result);
	}

	@Override
	public PsiBitVector psiXor(final PsiBitVector obj)
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
					return index<size; // TODO ???
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

	@Override
	public int length()
	{
		return size;
	}
	/*
	@Override
	public PsiInteger psiLength()
	{
		return new PsiInteger(size);
	}
	*/

	@Override
	public PsiBoolean psiIsEmpty()
	{
		return new PsiBoolean(bitvector.size()==0);
	}

	private java.util.BitSet bitvector;
	private int size;
}
