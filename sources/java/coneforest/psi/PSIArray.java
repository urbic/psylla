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

	public void invoke(PSIInterpreter interpreter)
	{
		if(isExecutable())
		{
			ExecutionStack execstack=interpreter.getExecutionStack();
			execstack.push(this);
			for(int i=0; i<size()-1; i++)
				get(i).execute(interpreter);
			execstack.pop();
			if(size()>0)
			{
				execstack.push(get(size()-1));
				get(size()-1).execute(interpreter);
				execstack.pop();
			}
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
		for(PSIObject obj: array)
			sb.append(obj+" ");
		sb.deleteCharAt(sb.length()-1);
		sb.append(isExecutable()? "}": "]");
		return sb.toString();
	}

	private java.util.ArrayList<PSIObject> array;
}
