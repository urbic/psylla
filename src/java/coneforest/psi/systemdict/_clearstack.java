package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _clearstack extends PsiOperator
{
	@Override
	public void invoke(Interpreter interpreter)
	{
		interpreter.getOperandStack().clear();
	}
}
