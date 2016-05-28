package coneforest.psi.core;

/**
 *	A representation of Ψ-{@code bitvector} object.
 */
public class PsiBitVector
	implements
		PsiArraylike<PsiBoolean>,
		PsiLogical<PsiBitVector>
{
	public PsiBitVector()
	{
		this(new java.util.BitSet());
	}

	public PsiBitVector(final java.util.BitSet bitvector)
	{
		this.bitvector=bitvector;
	}

	@Override
	public String getTypeName()
	{
		return "bitvector";
	}

	@Override
	public PsiBoolean get(final int index)
		throws PsiException
	{
		if(index>=size)
			throw new PsiRangeCheckException();
		try
		{
			return PsiBoolean.valueOf(bitvector.get(index));
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiRangeCheckException();
		}
	}

	@Override
	public PsiBitVector psiGetInterval(final PsiInteger oIndex, final PsiInteger oCount)
		throws PsiException
	{
		int index=oIndex.intValue();
		int count=oCount.intValue();
		if(index+count>size)
			throw new PsiRangeCheckException();
		try
		{
			final PsiBitVector newBitvector
				=new PsiBitVector(bitvector.get(index, index+count));
			newBitvector.size=count;
			return newBitvector;
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiRangeCheckException();
		}
	}

	@Override
	public void put(final int index, final PsiBoolean oBoolean)
		throws PsiException
	{
		if(index>=size)
			throw new PsiRangeCheckException();
		try
		{
			bitvector.set(index, oBoolean.booleanValue());
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiRangeCheckException();
		}
	}

	@Override
	public void psiPutInterval(final PsiInteger oIndex, PsiIterable<? extends PsiBoolean> oIterable)
		throws PsiException
	{
		int index=oIndex.intValue();
		if(index<0
				||
				oIterable instanceof PsiLengthy
				&& index+((PsiLengthy)oIterable).length()>=size)
			throw new PsiRangeCheckException();
		for(PsiBoolean oBoolean: oIterable)
		{
			bitvector.set(index++, oBoolean.booleanValue());
			if(index==size)
				break;
		}
	}

	@Override
	public void psiAppend(final PsiBoolean oBoolean)
	{
		bitvector.set(size++, oBoolean.booleanValue());
	}

	@Override
	public void insert(final int index, final PsiBoolean oBoolean)
	{
		size++;
		for(int i=size-1; i>index; i--)
			bitvector.set(i, bitvector.get(i-1));
		bitvector.set(index, oBoolean.booleanValue());
	}

	@Override
	public void delete(final int index)
		throws PsiException
	{
		try
		{
			for(int i=index; i<size; i++)
				bitvector.set(i-1, bitvector.get(i));
			size--;
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiRangeCheckException();
		}
	}

	@Override
	public PsiBoolean extract(int indexValue)
		throws PsiException
	{
		try
		{
			PsiBoolean result=get(indexValue);
			for(int i=indexValue; i<size; i++)
				bitvector.set(i-1, bitvector.get(i));
			size--;
			return result;
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiRangeCheckException();
		}
	}

	@Override
	public PsiBitVector psiExtractInterval(final PsiInteger oStart, final PsiInteger oCount)
		throws PsiException
	{
		final PsiBitVector oResult=psiGetInterval(oStart, oCount);
		int start=oStart.intValue();
		int count=oCount.intValue();
		for(int i=start+count; i<size; i++)
			bitvector.set(i-count, bitvector.get(i));
		size-=count;
		return oResult;
	}

	@Override
	public PsiBitVector psiNot()
	{
		final java.util.BitSet result=(java.util.BitSet)bitvector.clone();
		result.flip(0, result.size());
		return new PsiBitVector(result);
	}

	@Override
	public PsiBitVector psiOr(final PsiBitVector oBitVector)
	{
		java.util.BitSet result=(java.util.BitSet)bitvector.clone();
		result.or(oBitVector.bitvector);
		return new PsiBitVector(result);
	}

	@Override
	public PsiBitVector psiAnd(final PsiBitVector oBitVector)
	{
		java.util.BitSet result=(java.util.BitSet)bitvector.clone();
		result.and(oBitVector.bitvector);
		return new PsiBitVector(result);
	}

	@Override
	public PsiBitVector psiXor(final PsiBitVector oBitVector)
	{
		java.util.BitSet result=(java.util.BitSet)bitvector.clone();
		result.xor(oBitVector.bitvector);
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
						return PsiBoolean.valueOf(bitvector.get(index++));
					//else
					//	throw new java.util.NoSuchElementException();
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
	public PsiBitVector psiSlice(final PsiIterable<PsiInteger> oIndices)
		throws PsiException
	{
		final PsiBitVector oResult=new PsiBitVector();
		for(PsiInteger oIndex: oIndices)
			oResult.psiAppend(psiGet(oIndex));
		return oResult;
	}

	@Override
	public void psiSetLength(final PsiInteger oLength)
		throws PsiException
	{
		final long length=oLength.longValue();
		if(length<0)
			throw new PsiRangeCheckException();
		if(length>Integer.MAX_VALUE)
			throw new PsiLimitCheckException();
		final int i=length();
		if(length<i)
			bitvector.clear((int)length, i);
		else
			bitvector.clear(i, (int)length);
		size=(int)length;
	}

	@Override
	public PsiBitVector psiClone()
	{
		return new PsiBitVector((java.util.BitSet)bitvector.clone());
	}

	@Override
	public void psiClear()
	{
		bitvector.clear();
	}


	private final java.util.BitSet bitvector;
	private int size;
}
