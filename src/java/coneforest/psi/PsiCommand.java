package coneforest.psi;

public class PsiCommand
	extends PsiName
{
	public PsiCommand(String nameString)
	{
		super(nameString);
	}

	@Override
	public void execute()
	{
		Interpreter interpreter=Interpreter.currentInterpreter();
		try
		{
			interpreter.getDictStack().load(this).invoke();
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
