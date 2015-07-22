package coneforest.psi.core;
import coneforest.psi.*;

public class _forall extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		final PsiIterable iterable=(PsiIterable)ops[0];
		final PsiObject proc=ops[1];

		if(iterable instanceof PsiDictlike)
		{
			final int loopLevel=interpreter.pushLoopLevel();
			for(java.util.Map.Entry<String, PsiObject> entry:
						(PsiIterable<java.util.Map.Entry<String, PsiObject>>)iterable)
			{
				opstack.push(new PsiName(entry.getKey()));
				opstack.push(entry.getValue());
				proc.invoke(interpreter);
				interpreter.handleExecutionStack(loopLevel);
				if(interpreter.getStopFlag() || interpreter.getExitFlag())
					break;
			}
			interpreter.popLoopLevel();
			interpreter.setExitFlag(false);
		}
		else if(iterable instanceof PsiIterable)
		{
			final int loopLevel=interpreter.pushLoopLevel();
			for(PsiObject element: (PsiIterable<? extends PsiObject>)iterable)
			{
				opstack.push(element);
				proc.invoke(interpreter);
				interpreter.handleExecutionStack(loopLevel);
				if(interpreter.getStopFlag() || interpreter.getExitFlag())
					break;
			}
			interpreter.popLoopLevel();
			interpreter.setExitFlag(false);
		}
		else
			throw new PsiException("typecheck");
	}
}
