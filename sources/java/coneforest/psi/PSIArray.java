package coneforest.psi;

public class PSIArray extends PSIObject
{
	public PSIArray()
	{
		array=new java.util.ArrayList<PSIObject>();
	}

	public byte getType()
	{
		return TYPE_ARRAY;
	}

	public void execute(PSIInterpreter interpreter)
	{
		//System.out.println("PSIArray.execute called!");
		interpreter.getOperandStack().push(this);
	}

	public void invoke(PSIInterpreter interpreter)
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

	public PSIObject get(int index)
	{
		return array.get(index);
	}

	public void add(PSIObject obj)
	{
		array.add(obj);
	}

	public void add(int i, PSIObject obj)
	{
		array.add(i, obj);
	}

	public void set(int index, PSIObject obj)
	{
		array.set(index, obj);
	}

	public String toString()
	{
		StringBuilder sb=new StringBuilder();
		sb.append(isExecutable()? "{": "[");
		if(size()>0)
		{
			for(PSIObject obj: array)
				sb.append(obj+" ");
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append(isExecutable()? "}": "]");
		return sb.toString();
	}

	private java.util.ArrayList<PSIObject> array;
}
