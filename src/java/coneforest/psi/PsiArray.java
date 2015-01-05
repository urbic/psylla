package coneforest.psi;

/**
 *	A representation of Ψ <code class="type">array</code> object.
 */
public class PsiArray
	extends PsiAbstractArray<PsiObject>
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

	public void execute(final Interpreter interpreter)
	{
		interpreter.getOperandStack().push(this);
	}

	public void invoke(final Interpreter interpreter)
	{
		if(isExecutable())
		{
			try
			{
				ExecutionStack execstack=interpreter.getExecutionStack();
				//int level=execstack.size();
				for(int i=array.size()-1; i>=0; i--)
					execstack.push(psiGet(i));
				//interpreter.handleExecutionStack(level);
			}
			catch(PsiException e)
			{
			}
		}
		else
			execute(interpreter);
	}

	@Override
	public int length()
	{
		return array.size();
	}

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
	public PsiBoolean psiIsEmpty()
	{
		return new PsiBoolean(array.isEmpty());
	}

	@Override
	public PsiObject psiGet(final int indexValue)
		throws PsiException
	{
		try
		{
			return array.get(indexValue);
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiException("rangecheck");
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
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiException("rangecheck");
		}
	}

	@Override
	public void psiAppend(final PsiObject obj)
	{
		array.add(obj);
	}

	@Override
	public void psiInsert(final int indexValue, final PsiObject obj)
		throws PsiException
	{
		try
		{
			array.add(indexValue, obj);
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiException("rangecheck");
		}
	}

	@Override
	public void psiPut(final int indexValue, final PsiObject obj)
		throws PsiException
	{
		try
		{
			array.set(indexValue, obj);
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiException("rangecheck");
		}
	}

	@Override
	public PsiObject psiDelete(final int indexValue)
		throws PsiException
	{
		try
		{
			return array.remove(indexValue);
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiException("rangecheck");
		}
	}

	@Override
	public PsiArray psiDeleteInterval(final PsiInteger start, final PsiInteger count)
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

	/*
	@Override
	public String toString()
	{
		StringBuilder sb=new StringBuilder();
		sb.append(isExecutable()? "{": "[");
		if(array.size()>0)
		{
			for(PsiObject obj: array)
				sb.append(obj+" ");
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append(isExecutable()? "}": "]");
		return sb.toString();
	}
	*/

	private java.util.ArrayList<PsiObject> array;
}
