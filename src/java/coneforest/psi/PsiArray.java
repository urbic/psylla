package coneforest.psi;

public class PsiArray extends PsiObject
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
			ExecutionStack execstack=interpreter.getExecutionStack();
			int level=execstack.size();
			for(int i=size()-1; i>=0; i--)
				execstack.push(get(i));
			//interpreter.handleExecutionStack(level);
		}
		else
			execute(interpreter);
	}

	public int size()
	{
		return array.size();
	}

	public PsiObject get(int index)
	{
		return array.get(index);
	}

	public void add(PsiObject obj)
	{
		array.add(obj);
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
