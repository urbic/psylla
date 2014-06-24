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

	public String getName()
	{
		return getClass().getSimpleName().substring(1);
	}
}
