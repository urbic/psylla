package coneforest.psi.core;
import coneforest.psi.*;

public class _mark extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		interpreter.getOperandStack().push(PsiMark.MARK);
	}
}
