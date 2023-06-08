package coneforest.psylla.core.types;

import coneforest.psylla.Type;
import coneforest.psylla.core.errors.PsyError;
import coneforest.psylla.core.errors.PsyLimitCheck;
import coneforest.psylla.core.errors.PsyRangeCheck;
import java.util.BitSet;
import java.util.Iterator;

/**
*	A representation of {@code bitarray}.
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
		this(new BitSet(), 0);
	}

	public PsyBitArray(final BitSet bitarray, final int size)
	{
		this.bitarray=bitarray;
		this.size=size;
	}

	@Override
	public PsyBoolean get(final int index)
		throws PsyError
	{
		if(index>=size)
			throw new PsyRangeCheck();
		try
		{
			return PsyBoolean.of(bitarray.get(index));
		}
		catch(final IndexOutOfBoundsException ex)
		{
			throw new PsyRangeCheck();
		}
	}

	@Override
	public PsyBitArray psyGetInterval(final PsyInteger oIndex, final PsyInteger oCount)
		throws PsyError
	{
		final var index=oIndex.intValue();
		final var count=oCount.intValue();
		if(index+count>size)
			throw new PsyRangeCheck();
		try
		{
			final var newBitArray=new PsyBitArray(bitarray.get(index, index+count), count);
			newBitArray.size=count;
			return newBitArray;
		}
		catch(final IndexOutOfBoundsException ex)
		{
			throw new PsyRangeCheck();
		}
	}

	@Override
	public void put(final int index, final PsyBoolean oBoolean)
		throws PsyError
	{
		if(index>=size)
			throw new PsyRangeCheck();
		try
		{
			bitarray.set(index, oBoolean.booleanValue());
		}
		catch(final IndexOutOfBoundsException ex)
		{
			throw new PsyRangeCheck();
		}
	}

	@Override
	public void psyPutInterval(final PsyInteger oIndex, PsyIterable<? extends PsyBoolean> oIterable)
		throws PsyError
	{
		int index=oIndex.intValue();
		if(index<0
				||
				oIterable instanceof PsyLengthy
				&& index+((PsyLengthy)oIterable).length()>=size)
			throw new PsyRangeCheck();
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
		throws PsyError
	{
		try
		{
			for(int i=index; i<size; i++)
				bitarray.set(i-1, bitarray.get(i));
			size--;
		}
		catch(final IndexOutOfBoundsException ex)
		{
			throw new PsyRangeCheck();
		}
	}

	@Override
	public PsyBoolean extract(int indexValue)
		throws PsyError
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
			throw new PsyRangeCheck();
		}
	}

	@Override
	public PsyBitArray psyExtractInterval(final PsyInteger oStart, final PsyInteger oCount)
		throws PsyError
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
		final var result=(BitSet)bitarray.clone();
		result.flip(0, size);
		return new PsyBitArray(result, size);
	}

	@Override
	public PsyBitArray psyOr(final PsyBitArray oBitArray)
	{
		final var result=(BitSet)bitarray.clone();
		result.or(oBitArray.bitarray);
		return new PsyBitArray(result, size>oBitArray.size? size: oBitArray.size);
	}

	@Override
	public PsyBitArray psyAnd(final PsyBitArray oBitArray)
	{
		final var result=(BitSet)bitarray.clone();
		result.and(oBitArray.bitarray);
		return new PsyBitArray(result, size>oBitArray.size? size: oBitArray.size);
	}

	@Override
	public PsyBitArray psyXor(final PsyBitArray oBitArray)
	{
		final var result=(BitSet)bitarray.clone();
		result.xor(oBitArray.bitarray);
		return new PsyBitArray(result, size>oBitArray.size? size: oBitArray.size);
	}

	@Override
	public PsyBitArray psyBitShift(final PsyInteger oShift)
	{
		final var shift=oShift.intValue();
		final var result=new BitSet();
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
		throws PsyRangeCheck
	{
		final int bit=oBit.intValue();
		if(bit<0)
			throw new PsyRangeCheck();
		return PsyBoolean.of(bitarray.get(bit));
	}

	@Override
	public PsyBitArray psySetBit(final PsyInteger oBit)
		throws PsyRangeCheck
	{
		final var bit=oBit.intValue();
		final var result=(BitSet)bitarray.clone();
		if(bit<0)
			throw new PsyRangeCheck();
		result.set(bit, true);
		return new PsyBitArray(result, bit<size? size: bit+1);
	}

	@Override
	public PsyBitArray psyFlipBit(final PsyInteger oBit)
		throws PsyRangeCheck
	{
		final var bit=oBit.intValue();
		final var result=(BitSet)bitarray.clone();
		if(bit<0)
			throw new PsyRangeCheck();
		result.set(bit, !result.get(bit));
		return new PsyBitArray(result, size);
	}

	@Override
	public PsyBitArray psyClearBit(final PsyInteger oBit)
		throws PsyRangeCheck
	{
		final var bit=oBit.intValue();
		final var result=(BitSet)bitarray.clone();
		if(bit<0)
			throw new PsyRangeCheck();
		result.set(bit, false);
		return new PsyBitArray(result, size);
	}

	@Override
	public Iterator<PsyBoolean> iterator()
	{
		return new Iterator<PsyBoolean>()
			{
				public boolean hasNext()
				{
					return index<size; // TODO ???
				}

				public PsyBoolean next()
				{
					//if(hasNext())
						return PsyBoolean.of(bitarray.get(index++));
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
		throws PsyError
	{
		final var oResult=new PsyBitArray();
		for(final var oIndex: oIndices)
			oResult.psyAppend(psyGet(oIndex));
		return oResult;
	}

	@Override
	public void psySetLength(final PsyInteger oLength)
		throws PsyError
	{
		final var length=oLength.longValue();
		if(length<0)
			throw new PsyRangeCheck();
		if(length>Integer.MAX_VALUE)
			throw new PsyLimitCheck();
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
		return new PsyBitArray((BitSet)bitarray.clone(), size);
	}

	@Override
	public void psyClear()
	{
		bitarray.clear();
	}

	private final BitSet bitarray;
	private int size;

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity01
				("bitarray", PsyBitArray::new),
		};

}
