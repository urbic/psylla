package coneforest.psi.core;
import coneforest.psi.*;

public class _time extends PsiOperator
{
	@Override
	public void invoke(Interpreter interpreter)
	{
		interpreter.getOperandStack().push(new PsiInteger((new java.util.Date()).getTime()));
	}
}
