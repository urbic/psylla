package coneforest.psylla.core;
import coneforest.psylla.*;
import java.util.ArrayList;
import java.util.Collections;

/**
*	A representation of {@code array} object.
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
		throws PsyRangeCheckException
	{
		try
		{
			return array.get(indexValue<0? indexValue+length(): indexValue);
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
		throws PsyErrorException
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
