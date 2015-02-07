package coneforest.psi.core;
import coneforest.psi.*;

public class _stringwriter extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		final OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.handleError("stackunderflow", this);
			return;
		}

		final PsiObject string=opstack.pop();
		try
		{
			opstack.push(new PsiStringWriter((PsiString)string));
		}
		catch(ClassCastException e)
		{
			opstack.push(string);
			interpreter.handleError(e, this);
		}
	}
}
