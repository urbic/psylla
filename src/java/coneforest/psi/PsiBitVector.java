package coneforest.psi;

/**
 *	A representation of Ψ <code class="type">bitvector</code> object.
 */
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
	public PsiBoolean psiGet(int index)
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

	@Override
	public PsiBitVector psiGetInterval(PsiInteger index, PsiInteger count)
		throws PsiException
	{
		/*
		PsiBitVector newBitVector=new PsiBitVector();
		int indexValue=index.getValue().intValue();
		int countValue=count.getValue().intValue();
		if(indexValue<0 || indexValue+countValue>=size)
			throw new PsiException("rangecheck");
		for(int i=0; i<countValue && i<size; i++)
			newBitVector.bitvector.set(i, bitvector.get(i+indexValue));
		newBitVector.size=countValue;
		return newBitVector;
		*/
		int indexValue=index.intValue();
		int countValue=count.intValue();
		if(indexValue+countValue>size)
			throw new PsiException("rangecheck");
		try
		{
			PsiBitVector newBitvector=new PsiBitVector(bitvector.get(indexValue, indexValue+countValue));
			newBitvector.size=countValue;
			return newBitvector;
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiException("rangecheck");
		}
	}

	@Override
	public void psiPut(int index, PsiBoolean bool)
		throws PsiException
	{
		if(index>=size)
			throw new PsiException("rangecheck");
		try
		{
			bitvector.set(index, bool.booleanValue());
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiException("rangecheck");
		}
	}

	@Override
	public void psiPutInterval(PsiInteger index, PsiIterable<? extends PsiBoolean> iterable)
		throws PsiException
	{
		int indexValue=index.intValue();
		if(indexValue<0
				||
				iterable instanceof PsiLengthy
				&& indexValue+((PsiLengthy)iterable).length()>=size)
			throw new PsiException("rangecheck");
		for(PsiBoolean bool: iterable)
		{
			bitvector.set(indexValue++, bool.booleanValue());
			if(indexValue==size)
				break;
		}

	}

	@Override
	public void psiAppend(PsiBoolean bool)
	{
		bitvector.set(size++, bool.booleanValue());
	}

	@Override
	public void psiInsert(int indexValue, PsiBoolean bool)
	{
		size++;
		for(int i=size-1; i>indexValue; i--)
			bitvector.set(i, bitvector.get(i-1));
		bitvector.set(indexValue, bool.booleanValue());
	}

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
					//if(hasNext())
						return new PsiBoolean(bitvector.get(index++));
					//else
					//	throw new java.util.NoSuchElementException();
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

	@Override
	public PsiBitVector psiSlice(PsiIterable<PsiInteger> indices)
		throws PsiException
	{
		PsiBitVector values=new PsiBitVector();
		for(PsiInteger index: indices)
			values.psiAppend(psiGet(index));
		return values;
	}

	@Override
	public PsiBoolean psiIsEmpty()
	{
		return new PsiBoolean(bitvector.size()==0);
	}

	@Override
	public void psiClear()
	{
		bitvector.clear();
	}


	private java.util.BitSet bitvector;
	private int size;
}
