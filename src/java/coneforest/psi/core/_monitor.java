package coneforest.psi.core;
import coneforest.psi.*;

public final class _monitor extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		final PsiLock lock=(PsiLock)ops[0];
		final PsiObject obj=ops[1];

		if(lock.isHeldByCurrentThread())
			throw new PsiInvalidContextException();
		lock.lock();
		try
		{
			int execlevel=interpreter.getExecLevel();
			obj.invoke(interpreter);
			interpreter.handleExecutionStack(execlevel);
		}
		finally
		{
			lock.unlock();
		}
	}
}
