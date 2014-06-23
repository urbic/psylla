package coneforest.psi;

public abstract class PSIOperator extends PSIObject
{
	public byte getType()
	{
		return TYPE_OPERATOR;
	}

	public void execute(PSIInterpreter interpreter)
	{
		if(isExecutable())
		{
			int level=interpreter.getExecutionStack().size();
			this.execute(interpreter);
			interpreter.handleExecutionStack(level);
		}
		else
			interpreter.getOperandStack().push(this);
	}

	public void invoke(PSIInterpreter interpreter)
	{
		execute(interpreter);
	}

	public String toString()
	{
		return "--"+getName()+"--";
	}

	// TODO
	/*
	protected String getName()
	{
		String name=null;
		try
		{
			name=(String)getClass().getField("name").get(null);
		}
		catch(IllegalAccessException e)
		{
			// TODO
			System.out.println("ILLEGAL ACCESS EXCEPTION");
		}
		catch(NoSuchFieldException e)
		{
			// TODO
			System.out.println("NO SUCH FIELD EXCEPTION");
		}
		return name;
	}
	*/
	abstract public String getName();
}
