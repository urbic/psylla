package coneforest.psi.core;
import coneforest.psi.*;

public class _div extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		final OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.handleError("stackunderflow", this);
			return;
		}

		final PsiObject arithmetic2=opstack.pop();
		final PsiObject arithmetic1=opstack.pop();
		try
		{
			opstack.push(((PsiArithmetic)arithmetic1).psiDiv((PsiArithmetic)arithmetic2));
		}
		catch(ClassCastException e)
		{
			opstack.push(arithmetic1);
			opstack.push(arithmetic2);
			interpreter.handleError(e, this);
		}
	}
}
