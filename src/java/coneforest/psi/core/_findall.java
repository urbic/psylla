package coneforest.psi.core;
import coneforest.psi.*;

public final class _findall extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		final PsiObject[] ops=ostack.popOperands(3);
		final PsiMatcher matcher=new PsiMatcher((PsiStringy)ops[0], (PsiRegExp)ops[1]);
		final PsiObject obj=ops[2];

		final int loopLevel=interpreter.pushLoopLevel();
		while(true)
		{
			if(matcher.psiFind().booleanValue())
			{
				ostack.push(matcher);
				obj.invoke(interpreter);
				interpreter.handleExecutionStack(loopLevel);
				if(interpreter.getStopFlag() || interpreter.getExitFlag())
					break;
			}
			else
				break;
		}
		interpreter.popLoopLevel();
		interpreter.setExitFlag(false);
	}
}
