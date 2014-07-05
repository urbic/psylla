package coneforest.psi;

public class PsiArray extends PsiObject implements Iterable<PsiObject>
{
	public PsiArray()
	{
		array=new java.util.ArrayList<PsiObject>();
	}

	public String getTypeName() { return "array"; }

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
				int level=execstack.size();
				for(int i=size()-1; i>=0; i--)
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

	public java.util.Iterator<PsiObject> iterator()
	{
		return array.iterator();
	}

	public int size()
	{
		return array.size();
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

	public PsiObject get(PsiInteger oIndex)
		throws PsiException
	{
		return get(oIndex.getValue().intValue());
	}

	public void add(PsiObject obj)
	{
		array.add(obj);
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

	public void put(PsiInteger oIndex, PsiObject obj)
		throws PsiException
	{
		put(oIndex.getValue().intValue(), obj);
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
		if(size()>0)
		{
			for(PsiObject obj: array)
				sb.append(obj+" ");
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append(isExecutable()? "}": "]");
		return sb.toString();
	}

	private java.util.ArrayList<PsiObject> array;
}
