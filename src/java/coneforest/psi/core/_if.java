package coneforest.psi.core;
import coneforest.psi.*;

public class _if extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		if(((PsiBoolean)ops[0]).booleanValue())
			ops[1].invoke(interpreter);
	}
}
