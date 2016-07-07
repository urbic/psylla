package coneforest.psi.core;

/**
*	A representation of Ψ-{@code bitarray} object.
*/
public class PsiBitArray
	implements
		PsiArraylike<PsiBoolean>,
		PsiLogical<PsiBitArray>
{
	public PsiBitArray()
	{
		this(new java.util.BitSet(), 0);
	}

	public PsiBitArray(final java.util.BitSet bitarray, final int size)
	{
		this.bitarray=bitarray;
		this.size=size;
	}

	@Override
	public String typeName()
	{
		return "bitarray";
	}

	@Override
	public PsiBoolean get(final int index)
		throws PsiException
	{
		if(index>=size)
			throw new PsiRangeCheckException();
		try
		{
			return PsiBoolean.valueOf(bitarray.get(index));
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiRangeCheckException();
		}
	}

	@Override
	public PsiBitArray psiGetInterval(final PsiInteger oIndex, final PsiInteger oCount)
		throws PsiException
	{
		final int index=oIndex.intValue();
		final int count=oCount.intValue();
		if(index+count>size)
			throw new PsiRangeCheckException();
		try
		{
			final PsiBitArray newBitArray
				=new PsiBitArray(bitarray.get(index, index+count), count);
			newBitArray.size=count;
			return newBitArray;
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
			bitarray.set(index, oBoolean.booleanValue());
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
			bitarray.set(index++, oBoolean.booleanValue());
			if(index==size)
				break;
		}
	}

	@Override
	public void psiAppend(final PsiBoolean oBoolean)
	{
		bitarray.set(size++, oBoolean.booleanValue());
	}

	@Override
	public void insert(final int index, final PsiBoolean oBoolean)
	{
		size++;
		for(int i=size-1; i>index; i--)
			bitarray.set(i, bitarray.get(i-1));
		bitarray.set(index, oBoolean.booleanValue());
	}

	@Override
	public void delete(final int index)
		throws PsiException
	{
		try
		{
			for(int i=index; i<size; i++)
				bitarray.set(i-1, bitarray.get(i));
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
			final PsiBoolean result=get(indexValue);
			for(int i=indexValue; i<size; i++)
				bitarray.set(i-1, bitarray.get(i));
			size--;
			return result;
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiRangeCheckException();
		}
	}

	@Override
	public PsiBitArray psiExtractInterval(final PsiInteger oStart, final PsiInteger oCount)
		throws PsiException
	{
		final PsiBitArray oResult=psiGetInterval(oStart, oCount);
		int start=oStart.intValue();
		int count=oCount.intValue();
		for(int i=start+count; i<size; i++)
			bitarray.set(i-count, bitarray.get(i));
		size-=count;
		return oResult;
	}

	@Override
	public PsiBitArray psiNot()
	{
		final java.util.BitSet result=(java.util.BitSet)bitarray.clone();
		result.flip(0, size);
		return new PsiBitArray(result, size);
	}

	@Override
	public PsiBitArray psiOr(final PsiBitArray oBitArray)
	{
		final java.util.BitSet result=(java.util.BitSet)bitarray.clone();
		result.or(oBitArray.bitarray);
		return new PsiBitArray(result, size>oBitArray.size? size: oBitArray.size);
	}

	@Override
	public PsiBitArray psiAnd(final PsiBitArray oBitArray)
	{
		final java.util.BitSet result=(java.util.BitSet)bitarray.clone();
		result.and(oBitArray.bitarray);
		return new PsiBitArray(result, size>oBitArray.size? size: oBitArray.size);
	}

	@Override
	public PsiBitArray psiXor(final PsiBitArray oBitArray)
	{
		final java.util.BitSet result=(java.util.BitSet)bitarray.clone();
		result.xor(oBitArray.bitarray);
		return new PsiBitArray(result, size>oBitArray.size? size: oBitArray.size);
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
						return PsiBoolean.valueOf(bitarray.get(index++));
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
	public PsiBitArray psiSlice(final PsiIterable<PsiInteger> oIndices)
		throws PsiException
	{
		final PsiBitArray oResult=new PsiBitArray();
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
			bitarray.clear((int)length, i);
		else
			bitarray.clear(i, (int)length);
		size=(int)length;
	}

	@Override
	public PsiBitArray psiClone()
	{
		return new PsiBitArray((java.util.BitSet)bitarray.clone(), size);
	}

	@Override
	public void psiClear()
	{
		bitarray.clear();
	}


	private final java.util.BitSet bitarray;
	private int size;
}
