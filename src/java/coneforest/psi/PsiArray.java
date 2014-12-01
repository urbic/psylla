package coneforest.psi;

/**
 *	A representation of Ψ <code class="type">array</code> object.
 */
public class PsiArray
	extends PsiAbstractArray<PsiObject>
{
	public PsiArray()
	{
	}

	public PsiArray(java.util.ArrayList<PsiObject> list)
	{
		array=list;
	}

	public PsiArray(PsiArray array)
	{
		this.array=(java.util.ArrayList<PsiObject>)array.array.clone();
	}

	public void execute(Interpreter interpreter)
	{
		interpreter.getOperandStack().push(this);
	}

	public void invoke(Interpreter interpreter)
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
	public PsiInteger psiLength()
	{
		return new PsiInteger(array.size());
	}

	@Override
	public PsiBoolean psiIsEmpty()
	{
		return new PsiBoolean(array.isEmpty());
	}

	@Override
	public PsiObject psiGet(int index)
		throws PsiException
	{
		try
		{
			return array.get(index);
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiException("rangecheck");
		}
	}

	@Override
	public PsiArray psiGetInterval(PsiInteger index, PsiInteger count)
		throws PsiException
	{
		try
		{
			return new PsiArray((java.util.ArrayList<PsiObject>)array.subList(index.intValue(), count.intValue()));
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiException("rangecheck");
		}
	}

	@Override
	public void psiAppend(PsiObject obj)
	{
		array.add(obj);
	}

	@Override
	public void psiInsert(int indexValue, PsiObject obj)
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
	public void psiInsertAll(PsiInteger index, PsiIterable<? extends PsiObject> iterable)
		throws PsiException
	{
		int indexValue=index.intValue();
		try
		{
			for(PsiObject obj: iterable)
				array.add(indexValue++, obj);
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiException("rangecheck");
		}
	}

	@Override
	public void psiPut(int indexValue, PsiObject obj)
		throws PsiException
	{
		array.set(indexValue, obj);
	}

	@Override
	public PsiArray psiSlice(PsiIterable<PsiInteger> indices)
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

	private java.util.ArrayList<PsiObject> array=new java.util.ArrayList<PsiObject>();
}
