package coneforest.psi;

/**
 *	A representation of Ψ-<code class="type">array</code> object.
 */
public class PsiArray
	implements PsiArraylike<PsiObject>
{
	public PsiArray()
	{
		this(new java.util.ArrayList<PsiObject>());
	}

	public PsiArray(final java.util.ArrayList<PsiObject> list)
	{
		array=list;
	}

	public PsiArray(final PsiArray array)
	{
		this.array=(java.util.ArrayList<PsiObject>)array.array.clone();
	}

	/**
	 *	@return <code class="constant">"real"</code>.
	 */
	@Override
	public String getTypeName()
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
		return new PsiArray(this);
	}

	@Override
	public PsiObject get(final int indexValue)
		throws PsiException
	{
		try
		{
			return array.get(indexValue);
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiRangeCheckException();
		}
	}

	@Override
	public PsiArray psiGetInterval(final PsiInteger start, final PsiInteger count)
		throws PsiException
	{
		try
		{
			return new PsiArray(new java.util.ArrayList<PsiObject>(array.subList(start.intValue(),
					start.intValue()+count.intValue())));
		}
		catch(IndexOutOfBoundsException|IllegalArgumentException e)
		{
			throw new PsiRangeCheckException();
		}
	}

	@Override
	public void psiAppend(final PsiObject obj)
		throws PsiException
	{
		if(length()==Integer.MAX_VALUE)
			throw new PsiLimitCheckException();
		array.add(obj);
	}

	@Override
	public void insert(final int indexValue, final PsiObject obj)
		throws PsiException
	{
		try
		{
			array.add(indexValue, obj);
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiRangeCheckException();
		}
	}

	@Override
	public void put(final int indexValue, final PsiObject obj)
		throws PsiException
	{
		try
		{
			array.set(indexValue, obj);
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiRangeCheckException();
		}
	}

	@Override
	public void delete(int indexValue)
		throws PsiException
	{
		try
		{
			array.remove(indexValue);
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiRangeCheckException();
		}
	}

	@Override
	public PsiObject extract(final int indexValue)
		throws PsiException
	{
		try
		{
			return array.remove(indexValue);
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiRangeCheckException();
		}
	}

	@Override
	public PsiArray psiExtractInterval(final PsiInteger start, final PsiInteger count)
		throws PsiException
	{
		PsiArray result=psiGetInterval(start, count);
		array.subList(start.intValue(), start.intValue()+count.intValue()).clear();
		return result;
	}

	@Override
	public PsiArray psiSlice(final PsiIterable<PsiInteger> indices)
		throws PsiException
	{
		PsiArray values=new PsiArray();
		for(PsiInteger index: indices)
			values.psiAppend(psiGet(index));
		return values;
	}

	@Override
	public void psiClear()
	{
		array.clear();
	}

	@Override
	public void psiSetLength(final PsiInteger length)
		throws PsiException
	{
		final long lengthValue=length.longValue();
		if(lengthValue<0)
			throw new PsiRangeCheckException();
		if(lengthValue>Integer.MAX_VALUE)
			throw new PsiLimitCheckException();
		int i=length();
		if(lengthValue<i)
			array.subList((int)lengthValue, i).clear();
		else
		{
			array.ensureCapacity((int)lengthValue);
			while(i++<lengthValue)
				array.add(PsiNull.NULL);
		}
	}

	public PsiArray psiSort(java.util.Comparator<? super PsiObject> comparator)
	{
		PsiArray result=psiClone();
		java.util.Collections.sort(result.array, comparator);
		return result;
	}

	public PsiInteger psiBinarySearch(PsiObject key, PsiProcedure comparator)
		throws PsiException
	{
		final Interpreter interpreter=(Interpreter)PsiContext.psiCurrentContext();
		final OperandStack opstack=interpreter.getOperandStack();
		return PsiInteger.valueOf(java.util.Collections.<PsiObject>binarySearch(array, key,
			new java.util.Comparator<PsiObject>()
				{
					@Override
					public int compare(PsiObject obj1, PsiObject obj2)
					{
						opstack.push(obj1);
						opstack.push(obj2);
						final int execLevel=interpreter.getExecLevel();
						comparator.invoke(interpreter);
						interpreter.handleExecutionStack(execLevel);
						// TODO: ensure stack size
						return ((PsiInteger)opstack.pop()).intValue();
					}
				}));
	}

	public PsiString psiUnite(PsiStringy separator)
		throws PsiException
	{
		try
		{
			final StringBuilder sb=new StringBuilder();
			final String separatorString=separator.getString();
			for(int i=0; i<length(); i++)
			{
				if(i>0)
					sb.append(separatorString);
				sb.append(((PsiStringy)get(i)).getString());
			}
			return new PsiString(sb);
		}
		catch(ClassCastException e)
		{
			throw new PsiTypeCheckException();
		}
	}

	private java.util.ArrayList<PsiObject> array;
}
