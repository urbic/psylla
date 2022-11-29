package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of {@code bitarray} object.
*/
@Type("bitarray")
public class PsyBitArray
	implements
		PsyFormalArray<PsyBoolean>,
		PsyBitwise<PsyBitArray>
{
	/**
	*	Creates a new empty {@code bitarray}.
	*/
	public PsyBitArray()
	{
		this(new java.util.BitSet(), 0);
	}

	public PsyBitArray(final java.util.BitSet bitarray, final int size)
	{
		this.bitarray=bitarray;
		this.size=size;
	}

	@Override
	public PsyBoolean get(final int index)
		throws PsyErrorException
	{
		if(index>=size)
			throw new PsyRangeCheckException();
		try
		{
			return PsyBoolean.valueOf(bitarray.get(index));
		}
		catch(final IndexOutOfBoundsException ex)
		{
			throw new PsyRangeCheckException();
		}
	}

	@Override
	public PsyBitArray psyGetInterval(final PsyInteger oIndex, final PsyInteger oCount)
		throws PsyErrorException
	{
		final var index=oIndex.intValue();
		final var count=oCount.intValue();
		if(index+count>size)
			throw new PsyRangeCheckException();
		try
		{
			final var newBitArray=new PsyBitArray(bitarray.get(index, index+count), count);
			newBitArray.size=count;
			return newBitArray;
		}
		catch(final IndexOutOfBoundsException ex)
		{
			throw new PsyRangeCheckException();
		}
	}

	@Override
	public void put(final int index, final PsyBoolean oBoolean)
		throws PsyErrorException
	{
		if(index>=size)
			throw new PsyRangeCheckException();
		try
		{
			bitarray.set(index, oBoolean.booleanValue());
		}
		catch(final IndexOutOfBoundsException ex)
		{
			throw new PsyRangeCheckException();
		}
	}

	@Override
	public void psyPutInterval(final PsyInteger oIndex, PsyIterable<? extends PsyBoolean> oIterable)
		throws PsyErrorException
	{
		int index=oIndex.intValue();
		if(index<0
				||
				oIterable instanceof PsyLengthy
				&& index+((PsyLengthy)oIterable).length()>=size)
			throw new PsyRangeCheckException();
		for(final var oBoolean: oIterable)
		{
			bitarray.set(index++, oBoolean.booleanValue());
			if(index==size)
				break;
		}
	}

	@Override
	public void psyAppend(final PsyBoolean oBoolean)
	{
		bitarray.set(size++, oBoolean.booleanValue());
	}

	@Override
	public void insert(final int index, final PsyBoolean oBoolean)
	{
		size++;
		for(int i=size-1; i>index; i--)
			bitarray.set(i, bitarray.get(i-1));
		bitarray.set(index, oBoolean.booleanValue());
	}

	@Override
	public void delete(final int index)
		throws PsyErrorException
	{
		try
		{
			for(int i=index; i<size; i++)
				bitarray.set(i-1, bitarray.get(i));
			size--;
		}
		catch(final IndexOutOfBoundsException ex)
		{
			throw new PsyRangeCheckException();
		}
	}

	@Override
	public PsyBoolean extract(int indexValue)
		throws PsyErrorException
	{
		try
		{
			final var result=get(indexValue);
			for(int i=indexValue; i<size; i++)
				bitarray.set(i-1, bitarray.get(i));
			size--;
			return result;
		}
		catch(final IndexOutOfBoundsException ex)
		{
			throw new PsyRangeCheckException();
		}
	}

	@Override
	public PsyBitArray psyExtractInterval(final PsyInteger oStart, final PsyInteger oCount)
		throws PsyErrorException
	{
		final var oResult=psyGetInterval(oStart, oCount);
		final var start=oStart.intValue();
		final var count=oCount.intValue();
		for(int i=start+count; i<size; i++)
			bitarray.set(i-count, bitarray.get(i));
		size-=count;
		return oResult;
	}

	@Override
	public PsyBitArray psyNot()
	{
		final var result=(java.util.BitSet)bitarray.clone();
		result.flip(0, size);
		return new PsyBitArray(result, size);
	}

	@Override
	public PsyBitArray psyOr(final PsyBitArray oBitArray)
	{
		final var result=(java.util.BitSet)bitarray.clone();
		result.or(oBitArray.bitarray);
		return new PsyBitArray(result, size>oBitArray.size? size: oBitArray.size);
	}

	@Override
	public PsyBitArray psyAnd(final PsyBitArray oBitArray)
	{
		final var result=(java.util.BitSet)bitarray.clone();
		result.and(oBitArray.bitarray);
		return new PsyBitArray(result, size>oBitArray.size? size: oBitArray.size);
	}

	@Override
	public PsyBitArray psyXor(final PsyBitArray oBitArray)
	{
		final var result=(java.util.BitSet)bitarray.clone();
		result.xor(oBitArray.bitarray);
		return new PsyBitArray(result, size>oBitArray.size? size: oBitArray.size);
	}

	@Override
	public PsyBitArray psyBitShift(final PsyInteger oShift)
	{
		final var shift=oShift.intValue();
		final var result=new java.util.BitSet();
		if(shift>=0)
		{
			for(int i=size-1; i>=0; i--)
				result.set(i+shift, bitarray.get(i));
		}
		else
		{
			for(int i=0+size; i<size; i++)
				result.set(i+shift, bitarray.get(i));
		}
		return new PsyBitArray(result, size+shift);
	}

	@Override
	public PsyBoolean psyTestBit(final PsyInteger oBit)
		throws PsyRangeCheckException
	{
		final int bit=oBit.intValue();
		if(bit<0)
			throw new PsyRangeCheckException();
		return PsyBoolean.valueOf(bitarray.get(bit));
	}

	@Override
	public PsyBitArray psySetBit(final PsyInteger oBit)
		throws PsyRangeCheckException
	{
		final var bit=oBit.intValue();
		final var result=(java.util.BitSet)bitarray.clone();
		if(bit<0)
			throw new PsyRangeCheckException();
		result.set(bit, true);
		return new PsyBitArray(result, bit<size? size: bit+1);
	}

	@Override
	public PsyBitArray psyFlipBit(final PsyInteger oBit)
		throws PsyRangeCheckException
	{
		final var bit=oBit.intValue();
		final var result=(java.util.BitSet)bitarray.clone();
		if(bit<0)
			throw new PsyRangeCheckException();
		result.set(bit, !result.get(bit));
		return new PsyBitArray(result, size);
	}

	@Override
	public PsyBitArray psyClearBit(final PsyInteger oBit)
		throws PsyRangeCheckException
	{
		final var bit=oBit.intValue();
		final var result=(java.util.BitSet)bitarray.clone();
		if(bit<0)
			throw new PsyRangeCheckException();
		result.set(bit, false);
		return new PsyBitArray(result, size);
	}

	@Override
	public java.util.Iterator<PsyBoolean> iterator()
	{
		return new java.util.Iterator<PsyBoolean>()
			{
				public boolean hasNext()
				{
					return index<size; // TODO ???
				}

				public PsyBoolean next()
				{
					//if(hasNext())
						return PsyBoolean.valueOf(bitarray.get(index++));
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
	public PsyBitArray psySlice(final PsyIterable<PsyInteger> oIndices)
		throws PsyErrorException
	{
		final var oResult=new PsyBitArray();
		for(final var oIndex: oIndices)
			oResult.psyAppend(psyGet(oIndex));
		return oResult;
	}

	@Override
	public void psySetLength(final PsyInteger oLength)
		throws PsyErrorException
	{
		final var length=oLength.longValue();
		if(length<0)
			throw new PsyRangeCheckException();
		if(length>Integer.MAX_VALUE)
			throw new PsyLimitCheckException();
		final var i=length();
		if(length<i)
			bitarray.clear((int)length, i);
		else
			bitarray.clear(i, (int)length);
		size=(int)length;
	}

	@Override
	public PsyBitArray psyClone()
	{
		return new PsyBitArray((java.util.BitSet)bitarray.clone(), size);
	}

	@Override
	public void psyClear()
	{
		bitarray.clear();
	}

	private final java.util.BitSet bitarray;
	private int size;

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity01
				("bitarray", PsyBitArray::new),
		};

}
