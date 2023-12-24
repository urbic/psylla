package coneforest.psylla.core;

import coneforest.psylla.*;
import java.util.ArrayList;
import java.util.Collections;

/**
*	The representation of {@code array}.
*/
@Type("array")
public class PsyArray
	implements PsyFormalArray<PsyObject>
{
	/**
	*	Constructs a new empty {@code array}.
	*/
	public PsyArray()
	{
		this(new ArrayList<PsyObject>());
	}

	/**
	*	Constructs a new {@code array} wrapped around the given array list.
	*
	*	@param array a given array list.
	*/
	public PsyArray(final ArrayList<PsyObject> array)
	{
		this.array=array;
	}

	@Override
	public int length()
	{
		return array.size();
	}

	@Override
	public java.util.Iterator<PsyObject> iterator()
	{
		return array.iterator();
	}

	@Override
	public PsyArray psyClone()
	{
		return new PsyArray((ArrayList<PsyObject>)array.clone());
	}

	@Override
	public PsyObject get(final int index)
		throws PsyRangeCheckException
	{
		try
		{
			return array.get(index<0? index+length(): index);
		}
		catch(final IndexOutOfBoundsException ex)
		{
			throw new PsyRangeCheckException();
		}
	}

	@Override
	public PsyArray psyGetInterval(final PsyInteger oStart, final PsyInteger oCount)
		throws PsyRangeCheckException
	{
		try
		{
			return new PsyArray(new ArrayList<PsyObject>(array.subList(oStart.intValue(),
					oStart.intValue()+oCount.intValue())));
		}
		catch(final IndexOutOfBoundsException|IllegalArgumentException ex)
		{
			throw new PsyRangeCheckException();
		}
	}

	@Override
	public void psyAppend(final PsyObject o)
		throws PsyLimitCheckException
	{
		if(length()==Integer.MAX_VALUE)
			throw new PsyLimitCheckException();
		array.add(o);
	}

	@Override
	public void insert(final int indexValue, final PsyObject o)
		throws PsyRangeCheckException
	{
		try
		{
			array.add(indexValue, o);
		}
		catch(final IndexOutOfBoundsException ex)
		{
			throw new PsyRangeCheckException();
		}
	}

	@Override
	public void put(final int indexValue, final PsyObject o)
		throws PsyRangeCheckException
	{
		try
		{
			//array.set(indexValue, o);
			array.set(indexValue<0? indexValue+length(): indexValue, o);
		}
		catch(final IndexOutOfBoundsException ex)
		{
			throw new PsyRangeCheckException();
		}
	}

	@Override
	public void delete(int indexValue)
		throws PsyRangeCheckException
	{
		try
		{
			array.remove(indexValue);
		}
		catch(final IndexOutOfBoundsException ex)
		{
			throw new PsyRangeCheckException();
		}
	}

	@Override
	public PsyObject extract(final int indexValue)
		throws PsyRangeCheckException
	{
		try
		{
			return array.remove(indexValue);
		}
		catch(final IndexOutOfBoundsException ex)
		{
			throw new PsyRangeCheckException();
		}
	}

	@Override
	public PsyArray psyExtractInterval(final PsyInteger oStart, final PsyInteger oCount)
		throws PsyRangeCheckException
	{
		final var oResult=psyGetInterval(oStart, oCount);
		array.subList(oStart.intValue(), oStart.intValue()+oCount.intValue()).clear();
		return oResult;
	}

	@Override
	public PsyArray psySlice(final PsyIterable<PsyInteger> oIndices)
		throws PsyRangeCheckException, PsyLimitCheckException
	{
		final var oValues=new PsyArray();
		for(final var oIndex: oIndices)
			oValues.psyAppend(psyGet(oIndex));
		return oValues;
	}

	@Override
	public void psyClear()
	{
		array.clear();
	}

	@Override
	public void psySetLength(final PsyInteger oLength)
		throws PsyRangeCheckException, PsyLimitCheckException
	{
		final var length=oLength.longValue();
		if(length<0)
			throw new PsyRangeCheckException();
		if(length>Integer.MAX_VALUE)
			throw new PsyLimitCheckException();
		int i=length();
		if(length<i)
			array.subList((int)length, i).clear();
		else
		{
			array.ensureCapacity((int)length);
			while(i++<length)
				array.add(PsyNull.NULL);
		}
	}

	/**
	*	Sorts this list according to the order induced by the specified comparator. The sort is
	*	stable: this method must not reorder equal elements.
	*
	*	@param oComparator the {@code proc} comparator used to compare array elements.
	*	@param oContext the execution context.
	*/
	public void psySort(final PsyProc oComparator, final PsyContext oContext)
	{
		array.sort(oComparator.asComparator(oContext));
	}

	/**
	*	Searches this array for the specified value using the binary search algorithm. The array
	*	must be sorted into ascending order according to the specified comparator. TODO
	*
	*	@param o the value to be searched for.
	*	@param oComparator the comparator by which the array is ordered.
	*	@param oContext the execution context.
	*	@return TODO
	*/
	public PsyInteger psyBinarySearch(final PsyObject o, final PsyProc oComparator, final PsyContext oContext)
	{
		return PsyInteger.of(Collections.<PsyObject>binarySearch(array, o,
				oComparator.asComparator(oContext)));
	}

	@Override
	public PsyStream psyStream()
	{
		return new PsyStream(array.stream());
	}

	protected final ArrayList<PsyObject> array;

	/**
	*	Context action of the {@code array} operator.
	*/
	@OperatorType("array")
	public static final ContextAction PSY_ARRAY
		=ContextAction.ofSupplier(PsyArray::new);

	/**
	*	Context action of the {@code arraytomark} operator.
	*/
	@OperatorType("arraytomark")
	public static final ContextAction PSY_ARRAYTOMARK=oContext->
		{
			final var ostack=oContext.operandStack();
			final var i=ostack.findMarkPosition();
			final var oArray=new PsyArray();
			for(int j=i+1; j<ostack.size(); j++)
				oArray.psyAppend(ostack.get(j));
			ostack.setSize(i);
			ostack.push(oArray);
		};

	/**
	*	Context action of the {@code binarysearch} operator.
	*/
	@OperatorType("binarysearch")
	public static final ContextAction PSY_BINARYSEARCH=oContext->
		{
			final var ostack=oContext.operandStackBacked(3);
			final var oIndex=ostack.<PsyArray>getBacked(0).psyBinarySearch(
					ostack.getBacked(1), ostack.getBacked(2), oContext);
			final var index=oIndex.intValue();
			if(index>=0)
			{
				ostack.push(oIndex);
				ostack.push(PsyBoolean.TRUE);
			}
			else
			{
				ostack.push(PsyInteger.of(-index-1));
				ostack.push(PsyBoolean.FALSE);
			}
		};

	/**
	*	Context action of the {@code sort} operator.
	*/
	@OperatorType("sort")
	public static final ContextAction PSY_SORT=oContext->
		{
			final var ostack=oContext.operandStackBacked(2);
			ostack.<PsyArray>getBacked(0).psySort(ostack.getBacked(1), oContext);
		};
}
