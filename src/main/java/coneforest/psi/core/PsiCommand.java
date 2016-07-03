package coneforest.psi.core;
import coneforest.psi.*;

public class PsiCommand
	extends PsiName
{
	public PsiCommand(final String name)
	{
		super(name);
	}

	@Override
	public void execute(final Interpreter interpreter)
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
