package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _null extends PsiOperator
{
	@Override
	public void execute(Interpreter interpreter)
	{
		interpreter.getOperandStack().push(new PsiNull());
	}
}
