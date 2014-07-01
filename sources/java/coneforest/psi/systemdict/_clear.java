package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _clear extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		interpreter.getOperandStack().clear();
	}
}
