package coneforest.psi;

public class PsiArray
	extends PsiAbstractArray<PsiObject>
{
	public PsiArray()
	{
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
					execstack.push(get(i));
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

	public PsiObject get(int index)
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
	public PsiObject psiGet(PsiInteger oIndex)
		throws PsiException
	{
		return get(oIndex.getValue().intValue());
	}

	@Override
	public void psiAppend(PsiObject obj)
	{
		array.add(obj);
	}

	/*
	@Override
	public void psiAppendAll(PsiIterable<? extends PsiObject> iterable)
	{
		for(PsiObject obj: iterable)
			psiAppend(obj);
	}
	*/

	@Override
	public void psiInsert(PsiInteger index, PsiObject obj)
		throws PsiException
	{
		try
		{
			array.add(index.getValue().intValue(), obj);
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
		int indexValue=index.getValue().intValue();
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

	public void put(int index, PsiObject obj)
		throws PsiException
	{
		try
		{
			array.set(index, obj);
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiException("rangecheck");
		}
	}

	@Override
	public void psiPut(PsiInteger integer, PsiObject obj)
		throws PsiException
	{
		put(integer.getValue().intValue(), obj);
	}

	public void add(int i, PsiObject obj)
	{
		array.add(i, obj);
	}

	public void set(int index, PsiObject obj)
	{
		array.set(index, obj);
	}

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

	private java.util.ArrayList<PsiObject> array=new java.util.ArrayList<PsiObject>();
}
