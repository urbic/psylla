package coneforest.psi.core;
import coneforest.psi.*;

public class _findall extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(3);
		final PsiMatcher matcher=new PsiMatcher((PsiStringlike)ops[0], (PsiRegExp)ops[1]);
		final PsiObject obj=ops[2];

		final int loopLevel=interpreter.pushLoopLevel();
		while(true)
		{
			if(matcher.psiFind().booleanValue())
			{
				opstack.push(matcher);
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
