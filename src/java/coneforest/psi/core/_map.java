package coneforest.psi.core;
import coneforest.psi.*;

public final class _map extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		final PsiObject[] ops=ostack.popOperands(2);
		final PsiContainer container=(PsiContainer)ops[0];
		final PsiProc proc=(PsiProc)ops[1];
		final PsiAppendable result=(PsiAppendable)container.psiNewEmpty();

		final int loopLevel=interpreter.pushLoopLevel();
		for(PsiObject element: (PsiContainer<? extends PsiObject>)container)
		{
			ostack.push(element);
			proc.invoke(interpreter);
			interpreter.handleExecutionStack(loopLevel);
			result.psiAppend(ostack.pop());
			if(interpreter.getStopFlag() || interpreter.getExitFlag())
				break;
		}
		interpreter.popLoopLevel();
		interpreter.setExitFlag(false);
		ostack.push(result);
	}
}
