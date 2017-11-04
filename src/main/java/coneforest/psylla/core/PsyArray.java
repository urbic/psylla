package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of Ψ-{@code array} object.
*/
@coneforest.psylla.Type("array")
public class PsyArray
	implements PsyArraylike<PsyObject>
{
	/**
	*	Creates a new empty Ψ-{@code array}.
	*/
	public PsyArray()
	{
		this(new java.util.ArrayList<PsyObject>());
	}

	/**
	*	Creates a new Ψ-{@code array} wrapped around the given array list.
	*
	*	@param array a given arary list.
	*/
	public PsyArray(final java.util.ArrayList<PsyObject> array)
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
		return new PsyArray((java.util.ArrayList<PsyObject>)array.clone());
	}

	@Override
	public PsyObject get(final int index)
		throws PsyRangeCheckException
	{
		try
		{
			return array.get(index);
		}
		catch(final IndexOutOfBoundsException e)
		{
			throw new PsyRangeCheckException();
		}
	}

	@Override
	public PsyArray psyGetInterval(final PsyInteger start, final PsyInteger oCount)
		throws PsyRangeCheckException
	{
		try
		{
			return new PsyArray(new java.util.ArrayList<PsyObject>(array.subList(start.intValue(),
					start.intValue()+oCount.intValue())));
		}
		catch(final IndexOutOfBoundsException|IllegalArgumentException e)
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
		catch(final IndexOutOfBoundsException e)
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
			array.set(indexValue, o);
		}
		catch(final IndexOutOfBoundsException e)
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
		catch(final IndexOutOfBoundsException e)
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
		catch(final IndexOutOfBoundsException e)
		{
			throw new PsyRangeCheckException();
		}
	}

	@Override
	public PsyArray psyExtractInterval(final PsyInteger oStart, final PsyInteger oCount)
		throws PsyRangeCheckException
	{
		final PsyArray oResult=psyGetInterval(oStart, oCount);
		array.subList(oStart.intValue(), oStart.intValue()+oCount.intValue()).clear();
		return oResult;
	}

	@Override
	public PsyArray psySlice(final PsyIterable<PsyInteger> oIndices)
		throws PsyException
	{
		final PsyArray oValues=new PsyArray();
		for(PsyInteger oIndex: oIndices)
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
		final long length=oLength.longValue();
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

	public PsyArray psySort(final java.util.Comparator<? super PsyObject> comparator)
	{
		final PsyArray result=psyClone();
		java.util.Collections.sort(result.array, comparator);
		return result;
	}

	public PsyInteger psyBinarySearch(final PsyObject o, final PsyProc oComparator)
	{
		final Interpreter interpreter=(Interpreter)PsyContext.psyCurrentContext();
		final OperandStack opstack=interpreter.operandStack();
		return PsyInteger.valueOf(java.util.Collections.<PsyObject>binarySearch(array, o,
			// TODO gap
			(o1, o2)->
			{
				opstack.push(o1);
				opstack.push(o2);
				final int execLevel=interpreter.execLevel();
				oComparator.invoke(interpreter);
				interpreter.handleExecutionStack(execLevel);
				// TODO: ensure stack size
				return ((PsyInteger)opstack.pop()).intValue();
			}));
	}

	protected final java.util.ArrayList<PsyObject> array;
}
