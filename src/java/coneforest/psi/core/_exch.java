package coneforest.psi.core;
import coneforest.psi.*;

public class _exch extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		opstack.push(ops[1]);
		opstack.push(ops[0]);
	}
}
