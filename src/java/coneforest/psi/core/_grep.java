package coneforest.psi.core;
import coneforest.psi.*;

public class _grep extends PsiOperator
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
		boolean check;

		final int loopLevel=interpreter.pushLoopLevel();
		for(PsiObject element: (PsiContainer<? extends PsiObject>)container)
		{
			opstack.push(element);
			proc.invoke(interpreter);
			interpreter.handleExecutionStack(loopLevel);
			check=((PsiBoolean)opstack.pop()).booleanValue();
			if(interpreter.getStopFlag() || interpreter.getExitFlag())
				break;
			if(check)
				result.psiAppend(element);
		}
		interpreter.popLoopLevel();
		interpreter.setExitFlag(false);
		opstack.push(result);
	}
}
