package coneforest.psi.core;
import coneforest.psi.*;

public final class _complexpolar extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		final PsiObject[] ops=ostack.popOperands(2);
		ostack.push(PsiComplex.psiFromPolar((PsiNumeric)ops[0], (PsiNumeric)ops[1]));
	}
}
