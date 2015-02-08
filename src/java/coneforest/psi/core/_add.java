package coneforest.psi.core;
import coneforest.psi.*;

/**
 * The implementation of the <code>add</code> operator
 */
public class _add extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		opstack.push(((PsiArithmetic)ops[0]).psiAdd((PsiArithmetic)ops[1]));
	}
}
