package coneforest.psi.core;
import coneforest.psi.*;

public class _complexpolar extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		opstack.push(PsiComplex.psiFromPolar((PsiNumeric)ops[0], (PsiNumeric)ops[1]));
	}
}
