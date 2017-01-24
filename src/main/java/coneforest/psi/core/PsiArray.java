package coneforest.psi.core;
import coneforest.psi.*;

/**
*	A representation of Ψ-{@code array} object.
*/
public class PsiArray
	implements PsiArraylike<PsiObject>
{
	/**
	*	Creates a new empty Ψ-{@code array}.
	*/
	public PsiArray()
	{
		this(new java.util.ArrayList<PsiObject>());
	}

	/**
	*	Creates a new Ψ-{@code array} wrapped around the given array list.
	*
	*	@param array a given arary list.
	*/
	public PsiArray(final java.util.ArrayList<PsiObject> array)
	{
		this.array=array;
	}

	/**
	*	@return a string {@code "real"}.
	*/
	@Override
	public String typeName()
	{
		return "array";
	}

	@Override
	public int length()
	{
		return array.size();
	}

	@Override
	public java.util.Iterator<PsiObject> iterator()
	{
		return array.iterator();
	}

	@Override
	public PsiArray psiClone()
	{
		return new PsiArray((java.util.ArrayList<PsiObject>)array.clone());
	}

	@Override
	public PsiObject get(final int index)
		throws PsiRangeCheckException
	{
		try
		{
			return array.get(index);
		}
		catch(final IndexOutOfBoundsException e)
		{
			throw new PsiRangeCheckException();
		}
	}

	@Override
	public PsiArray psiGetInterval(final PsiInteger start, final PsiInteger oCount)
		throws PsiRangeCheckException
	{
		try
		{
			return new PsiArray(new java.util.ArrayList<PsiObject>(array.subList(start.intValue(),
					start.intValue()+oCount.intValue())));
		}
		catch(final IndexOutOfBoundsException|IllegalArgumentException e)
		{
			throw new PsiRangeCheckException();
		}
	}

	@Override
	public void psiAppend(final PsiObject o)
		throws PsiLimitCheckException
	{
		if(length()==Integer.MAX_VALUE)
			throw new PsiLimitCheckException();
		array.add(o);
	}

	@Override
	public void insert(final int indexValue, final PsiObject o)
		throws PsiRangeCheckException
	{
		try
		{
			array.add(indexValue, o);
		}
		catch(final IndexOutOfBoundsException e)
		{
			throw new PsiRangeCheckException();
		}
	}

	@Override
	public void put(final int indexValue, final PsiObject o)
		throws PsiRangeCheckException
	{
		try
		{
			array.set(indexValue, o);
		}
		catch(final IndexOutOfBoundsException e)
		{
			throw new PsiRangeCheckException();
		}
	}

	@Override
	public void delete(int indexValue)
		throws PsiRangeCheckException
	{
		try
		{
			array.remove(indexValue);
		}
		catch(final IndexOutOfBoundsException e)
		{
			throw new PsiRangeCheckException();
		}
	}

	@Override
	public PsiObject extract(final int indexValue)
		throws PsiRangeCheckException
	{
		try
		{
			return array.remove(indexValue);
		}
		catch(final IndexOutOfBoundsException e)
		{
			throw new PsiRangeCheckException();
		}
	}

	@Override
	public PsiArray psiExtractInterval(final PsiInteger oStart, final PsiInteger oCount)
		throws PsiRangeCheckException
	{
		final PsiArray oResult=psiGetInterval(oStart, oCount);
		array.subList(oStart.intValue(), oStart.intValue()+oCount.intValue()).clear();
		return oResult;
	}

	@Override
	public PsiArray psiSlice(final PsiIterable<PsiInteger> oIndices)
		throws PsiException
	{
		final PsiArray oValues=new PsiArray();
		for(PsiInteger oIndex: oIndices)
			oValues.psiAppend(psiGet(oIndex));
		return oValues;
	}

	@Override
	public void psiClear()
	{
		array.clear();
	}

	@Override
	public void psiSetLength(final PsiInteger oLength)
		throws PsiRangeCheckException, PsiLimitCheckException
	{
		final long length=oLength.longValue();
		if(length<0)
			throw new PsiRangeCheckException();
		if(length>Integer.MAX_VALUE)
			throw new PsiLimitCheckException();
		int i=length();
		if(length<i)
			array.subList((int)length, i).clear();
		else
		{
			array.ensureCapacity((int)length);
			while(i++<length)
				array.add(PsiNull.NULL);
		}
	}

	public PsiArray psiSort(final java.util.Comparator<? super PsiObject> comparator)
	{
		final PsiArray result=psiClone();
		java.util.Collections.sort(result.array, comparator);
		return result;
	}

	public PsiInteger psiBinarySearch(final PsiObject o, final PsiProc oComparator)
	{
		final Interpreter interpreter=(Interpreter)PsiContext.psiCurrentContext();
		final OperandStack opstack=interpreter.operandStack();
		return PsiInteger.valueOf(java.util.Collections.<PsiObject>binarySearch(array, o,
			// TODO gap
			(o1, o2)->
			{
				opstack.push(o1);
				opstack.push(o2);
				final int execLevel=interpreter.execLevel();
				oComparator.invoke(interpreter);
				interpreter.handleExecutionStack(execLevel);
				// TODO: ensure stack size
				return ((PsiInteger)opstack.pop()).intValue();
			}));
	}

	protected final java.util.ArrayList<PsiObject> array;
}
