package coneforest.psi.core;
import coneforest.psi.*;

/**
 * The implementation of the <code>abs</code> operator.
 * 
 */
public class _abs extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		opstack.push(((PsiArithmetic)opstack.popOperands(1)[0]).psiAbs());
	}
}
