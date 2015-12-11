package coneforest.psi.core;
import coneforest.psi.*;

public final class _external extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		ostack.push(((PsiClassLoader)interpreter.getSystemDict().get("classpath"))
				.psiExternal(((PsiStringy)ostack.popOperands(1)[0])));
	}
}
