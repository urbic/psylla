package coneforest.psi.core;
import coneforest.psi.*;

public final class _bitvector extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
	{
		interpreter.operandStack().push(new PsiBitVector());
	}
}
