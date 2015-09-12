package coneforest.psi.core;
import coneforest.psi.*;

public final class _map extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		final PsiContainer container=(PsiContainer)ops[0];
		final PsiProcedure proc=(PsiProcedure)ops[1];
		final PsiAppendable result=(PsiAppendable)container.psiNewEmpty();

		final int loopLevel=interpreter.pushLoopLevel();
		for(PsiObject element: (PsiContainer<? extends PsiObject>)container)
		{
			opstack.push(element);
			proc.invoke(interpreter);
			interpreter.handleExecutionStack(loopLevel);
			result.psiAppend(opstack.pop());
			if(interpreter.getStopFlag() || interpreter.getExitFlag())
				break;
		}
		interpreter.popLoopLevel();
		interpreter.setExitFlag(false);
		opstack.push(result);
	}
}
