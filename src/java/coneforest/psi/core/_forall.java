package coneforest.psi.core;
import coneforest.psi.*;

public class _forall extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		final OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.handleError("stackunderflow", this);
			return;
		}

		final PsiObject obj=opstack.pop();
		final PsiObject iterable=opstack.pop();

		if(iterable instanceof PsiDictionarylike)
		{
			///*
			final int loopLevel=interpreter.pushLoopLevel();
			for(java.util.Map.Entry<String, PsiObject> entry:
						(PsiIterable<java.util.Map.Entry<String, PsiObject>>)iterable)
			{
				if(interpreter.getExitFlag())
					break;
				opstack.push(new PsiName(entry.getKey()));
				opstack.push(entry.getValue());
				obj.invoke(interpreter);
				interpreter.handleExecutionStack(loopLevel);
			}
			interpreter.popLoopLevel();
			interpreter.setExitFlag(false);
			//*/
			//((PsiDictionarylike)iterable).psiForAll(obj, interpreter);
		}
		else if(iterable instanceof PsiIterable)
		{
			final int loopLevel=interpreter.pushLoopLevel();
			for(PsiObject element: ((PsiIterable<? extends PsiObject>)iterable))
			{
				if(interpreter.getExitFlag())
					break;
				opstack.push(element);
				obj.invoke(interpreter);
				interpreter.handleExecutionStack(loopLevel);
			}
			interpreter.popLoopLevel();
			interpreter.setExitFlag(false);
		}
		else
			interpreter.handleError("typecheck", this);
	}

}
