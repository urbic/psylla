package coneforest.psi;

public class PsiCommand
	extends PsiName
{
	public PsiCommand(String nameString)
	{
		super(nameString);
	}

	@Override
	public void execute(Interpreter interpreter)
	{
		try
		{
			//final int execLevel=interpreter.getExecLevel();
			interpreter.getDictStack().load(this).invoke(interpreter);
			//interpreter.getExecutionStack().push(interpreter.getDictStack().load(this));
			//System.out.println(getString()+ " invoked");
			//interpreter.handleExecutionStack(execLevel);
		}
		catch(PsiException e)
		{
			interpreter.handleError(e, this);
		}
	}

	@Override
	public String toSyntaxString()
	{
		return getString();
	}
}
