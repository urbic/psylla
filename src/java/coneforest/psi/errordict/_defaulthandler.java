package coneforest.psi.errordict;
import coneforest.psi.*;

public class _defaulthandler extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		try
		{
			System.out.println("DEFAULT HANDLER CALLED");
			interpreter.getSystemDictionary().psiGet("stop").invoke(interpreter);
		}
		catch(PsiException e)
		{
			// NOP
		}
	}
}
