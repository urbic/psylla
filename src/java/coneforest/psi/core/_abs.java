package coneforest.psi.core;
import coneforest.psi.*;

/**
 * The implementation of the <code>abs</code> operator.
 * 
 */
public final class _abs extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		ostack.push(((PsiComplexNumeric)ostack.popOperands(1)[0]).psiAbs());
	}
}
