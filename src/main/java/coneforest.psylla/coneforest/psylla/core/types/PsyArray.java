package coneforest.psylla.core.types;

import coneforest.psylla.Type;
import coneforest.psylla.core.errors.PsyError;
import coneforest.psylla.core.errors.PsyLimitCheck;
import coneforest.psylla.core.errors.PsyRangeCheck;
import java.util.ArrayList;
import java.util.Collections;

/**
*	A representation of {@code array}.
*/
@Type("array")
public class PsyArray
	implements PsyFormalArray<PsyObject>
{
	/**
	*	Creates a new empty {@code array}.
	*/
	public PsyArray()
	{
		this(new ArrayList<PsyObject>());
	}

	/**
	*	Creates a new {@code array} wrapped around the given array list.
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
	public PsyObject get(final int indexValue)
		throws PsyRangeCheck
	{
		try
		{
			return array.get(indexValue<0? indexValue+length(): indexValue);
		}
		catch(final IndexOutOfBoundsException ex)
		{
			throw new PsyRangeCheck();
		}
	}

	@Override
	public PsyArray psyGetInterval(final PsyInteger oStart, final PsyInteger oCount)
		throws PsyRangeCheck
	{
		try
		{
			return new PsyArray(new ArrayList<PsyObject>(array.subList(oStart.intValue(),
					oStart.intValue()+oCount.intValue())));
		}
		catch(final IndexOutOfBoundsException|IllegalArgumentException ex)
		{
			throw new PsyRangeCheck();
		}
	}

	@Override
	public void psyAppend(final PsyObject o)
		throws PsyLimitCheck
	{
		if(length()==Integer.MAX_VALUE)
			throw new PsyLimitCheck();
		array.add(o);
	}

	@Override
	public void insert(final int indexValue, final PsyObject o)
		throws PsyRangeCheck
	{
		try
		{
			array.add(indexValue, o);
		}
		catch(final IndexOutOfBoundsException ex)
		{
			throw new PsyRangeCheck();
		}
	}

	@Override
	public void put(final int indexValue, final PsyObject o)
		throws PsyRangeCheck
	{
		try
		{
			//array.set(indexValue, o);
			array.set(indexValue<0? indexValue+length(): indexValue, o);
		}
		catch(final IndexOutOfBoundsException ex)
		{
			throw new PsyRangeCheck();
		}
	}

	@Override
	public void delete(int indexValue)
		throws PsyRangeCheck
	{
		try
		{
			array.remove(indexValue);
		}
		catch(final IndexOutOfBoundsException ex)
		{
			throw new PsyRangeCheck();
		}
	}

	@Override
	public PsyObject extract(final int indexValue)
		throws PsyRangeCheck
	{
		try
		{
			return array.remove(indexValue);
		}
		catch(final IndexOutOfBoundsException ex)
		{
			throw new PsyRangeCheck();
		}
	}

	@Override
	public PsyArray psyExtractInterval(final PsyInteger oStart, final PsyInteger oCount)
		throws PsyRangeCheck
	{
		final var oResult=psyGetInterval(oStart, oCount);
		array.subList(oStart.intValue(), oStart.intValue()+oCount.intValue()).clear();
		return oResult;
	}

	@Override
	public PsyArray psySlice(final PsyIterable<PsyInteger> oIndices)
		throws PsyError
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
		throws PsyRangeCheck, PsyLimitCheck
	{
		final var length=oLength.longValue();
		if(length<0)
			throw new PsyRangeCheck();
		if(length>Integer.MAX_VALUE)
			throw new PsyLimitCheck();
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

	public void psySort(final PsyProc oComparator, final PsyContext oContext)
	{
		Collections.sort(array, oComparator.asComparator(oContext));
	}

	public PsyInteger psyBinarySearch(final PsyObject o, final PsyProc oComparator, final PsyContext oContext)
	{
		final var opstack=oContext.operandStack();
		return PsyInteger.of(Collections.<PsyObject>binarySearch(array, o,
				oComparator.asComparator(oContext)));
	}

	@Override
	public PsyStream psyStream()
	{
		return new PsyStream(array.stream());
	}

	protected final ArrayList<PsyObject> array;

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity01
				("array", PsyArray::new),
			new PsyOperator.Action
				("arraytomark", oContext->
					{
						final var ostack=oContext.operandStack();
						final var i=ostack.findMarkPosition();
						final var oArray=new PsyArray();
						for(int j=i+1; j<ostack.size(); j++)
							oArray.psyAppend(ostack.get(j));
						ostack.setSize(i);
						ostack.push(oArray);
					}),
			new PsyOperator.Action
				("binarysearch", oContext->
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
					}),
			new PsyOperator.Action
				("sort", oContext->
					{
						final var ostack=oContext.operandStackBacked(2);
						ostack.<PsyArray>getBacked(0).psySort(ostack.getBacked(1), oContext);
					}),
		};
}
