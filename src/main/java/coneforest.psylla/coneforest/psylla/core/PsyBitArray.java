package coneforest.psylla.core;

import coneforest.psylla.runtime.*;
import java.util.BitSet;
import java.util.Iterator;

/**
*	The representation of {@code bitarray}.
*/
@Type("bitarray")
public class PsyBitArray
	implements
		PsyFormalArray<PsyBoolean>,
		PsyBitwise<PsyBitArray>
{
	/**
	*	Context action of the {@code bitarray} operator.
	*/
	@OperatorType("bitarray")
	public static final ContextAction PSY_BITARRAY
		=ContextAction.ofSupplier(PsyBitArray::new);

	private final BitSet bitarray;
	private int size;

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
		throws PsyRangeCheckException
	{
		if(index>=size)
			throw new PsyRangeCheckException();
		try
		{
			return PsyBoolean.of(bitarray.get(index));
		}
		catch(final IndexOutOfBoundsException ex)
		{
			throw new PsyRangeCheckException(ex);
		}
	}

	@Override
	public PsyBitArray psyGetInterval(final PsyInteger oIndex, final PsyInteger oCount)
		throws PsyRangeCheckException
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
			throw new PsyRangeCheckException(ex);
		}
	}

	@Override
	public void put(final int index, final PsyBoolean oBoolean)
		throws PsyRangeCheckException
	{
		if(index>=size)
			throw new PsyRangeCheckException();
		try
		{
			bitarray.set(index, oBoolean.booleanValue());
		}
		catch(final IndexOutOfBoundsException ex)
		{
			throw new PsyRangeCheckException(ex);
		}
	}

	@Override
	public void psyPutInterval(final PsyInteger oIndex, final PsyIterable<? extends PsyBoolean> oIterable)
		throws PsyRangeCheckException
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
		throws PsyLimitCheckException, PsyRangeCheckException
	{
		if(size==Integer.MAX_VALUE)
			throw new PsyLimitCheckException();
		try
		{
			bitarray.set(size++, oBoolean.booleanValue());
		}
		catch(final IndexOutOfBoundsException e)
		{
			throw new PsyRangeCheckException();
		}
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
		throws PsyRangeCheckException
	{
		try
		{
			for(int i=index; i<size; i++)
				bitarray.set(i-1, bitarray.get(i));
			size--;
		}
		catch(final IndexOutOfBoundsException ex)
		{
			throw new PsyRangeCheckException(ex);
		}
	}

	@Override
	public PsyBoolean extract(final int indexValue)
		throws PsyRangeCheckException
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
			throw new PsyRangeCheckException(ex);
		}
	}

	@Override
	public PsyBitArray psyExtractInterval(final PsyInteger oStart, final PsyInteger oCount)
		throws PsyRangeCheckException
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
		throws PsyRangeCheckException
	{
		final int bit=oBit.intValue();
		if(bit<0)
			throw new PsyRangeCheckException();
		return PsyBoolean.of(bitarray.get(bit));
	}

	@Override
	public PsyBitArray psySetBit(final PsyInteger oBit)
		throws PsyRangeCheckException
	{
		final var bit=oBit.intValue();
		final var result=(BitSet)bitarray.clone();
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
		final var result=(BitSet)bitarray.clone();
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
		final var result=(BitSet)bitarray.clone();
		if(bit<0)
			throw new PsyRangeCheckException();
		result.set(bit, false);
		return new PsyBitArray(result, size);
	}

	@Override
	public Iterator<PsyBoolean> iterator()
	{
		return new Iterator<PsyBoolean>()
			{
				private int index=0;

				@Override
				public boolean hasNext()
				{
					return index<size; // TODO ???
				}

				@Override
				public PsyBoolean next()
				{
					return PsyBoolean.of(bitarray.get(index++));
				}
			};
	}

	@Override
	public int length()
	{
		return size;
	}

	@Override
	public PsyBitArray psySlice(final PsyIterable<PsyInteger> oIndices)
		throws PsyRangeCheckException, PsyLimitCheckException
	{
		final var oResult=new PsyBitArray();
		for(final var oIndex: oIndices)
			oResult.psyAppend(psyGet(oIndex));
		return oResult;
	}

	@Override
	public void psySetLength(final PsyInteger oLength)
		throws PsyLimitCheckException, PsyRangeCheckException
	{
		final var length=oLength.longValue();
		if(length<0)
			throw new PsyRangeCheckException();
		if(length>Integer.MAX_VALUE)
			throw new PsyLimitCheckException();
		final var s=size;
		if(length<s)
			bitarray.clear((int)length, s);
		else
			bitarray.clear(s, (int)length);
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

	@Override
	public String toSyntaxString()
	{
		final var sb=new StringBuilder("%bitarray=");
		int j=-1;
		for(int i=bitarray.nextSetBit(0); i>=0; i=bitarray.nextSetBit(i+1))
		{
			for(int k=j+1; k<i; k++)
				sb.append('0');
			sb.append('1');
			j=i;
		}
		sb.append('%');
		return sb.toString();
	}
}
