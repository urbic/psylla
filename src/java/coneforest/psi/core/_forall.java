package coneforest.psi.core;
import coneforest.psi.*;

public final class _forall extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		final PsiObject[] ops=ostack.popOperands(2);
		final PsiIterable iterable=(PsiIterable)ops[0];
		final PsiObject proc=ops[1];

		if(iterable instanceof PsiDictlike)
		{
			// TODO
			/*
			final int loopLevel=interpreter.pushLoopLevel();
			for(java.util.Map.Entry<String, PsiObject> entry:
						(PsiIterable<java.util.Map.Entry<String, PsiObject>>)iterable)
			{
				ostack.push(new PsiName(entry.getKey()));
				ostack.push(entry.getValue());
				proc.invoke(interpreter);
				interpreter.handleExecutionStack(loopLevel);
				if(interpreter.getStopFlag() || interpreter.getExitFlag())
					break;
			}
			interpreter.popLoopLevel();
			interpreter.setExitFlag(false);
			*/
		}
		else if(iterable instanceof PsiIterable)
		{
			/*
			final int loopLevel=interpreter.pushLoopLevel();
			for(PsiObject element: (PsiIterable<? extends PsiObject>)iterable)
			{
				ostack.push(element);
				proc.invoke(interpreter);
				interpreter.handleExecutionStack(loopLevel);
				if(interpreter.getStopFlag() || interpreter.getExitFlag())
					break;
			}
			interpreter.popLoopLevel();
			interpreter.setExitFlag(false);
			*/
			((PsiIterable<PsiObject>)iterable).psiForAll(proc);
		}
		else
			throw new PsiTypeCheckException();
	}
}
