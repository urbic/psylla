package coneforest.psi;

public abstract class PsiOperator extends PsiObject
{
	public PsiOperator()
	{
		super();
		setExecutable();
	}

	public String getTypeName() { return "operator"; }

	/*
	public void execute(Interpreter interpreter)
	{
		if(isExecutable())
		{
			System.out.println("EXECUTE OPERATOR");
			int level=interpreter.getExecutionStack().size();
			execute(interpreter);
			interpreter.handleExecutionStack(level);
		}
		else
			interpreter.getOperandStack().push(this);
	}
	*/

	public void invoke(Interpreter interpreter)
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
