package coneforest.psi.core;
import coneforest.psi.*;

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
			interpreter.dictStack().load(this).invoke(interpreter);
		}
		catch(PsiException e)
		{
			e.setEmitter(this);
			interpreter.handleError(e);
		}
	}

	@Override
	public String toSyntaxString()
	{
		return stringValue();
	}
}
