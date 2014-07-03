package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _false extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		interpreter.getOperandStack().push(new PsiBoolean(false));
	}
}
