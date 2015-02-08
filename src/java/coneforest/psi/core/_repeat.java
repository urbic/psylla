package coneforest.psi.core;
import coneforest.psi.*;

public class _repeat extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		final PsiInteger count=(PsiInteger)ops[0];
		final PsiObject obj=ops[1];
		
		long countValue=count.longValue();
		if(countValue<0)
			throw new PsiException("rangecheck");
		int loopLevel=interpreter.pushLoopLevel();
		for(int i=0;
				i<countValue && !interpreter.getExitFlag();
				i++)
		{
			obj.invoke(interpreter);
			interpreter.handleExecutionStack(loopLevel);
		}
		interpreter.popLoopLevel();
		interpreter.setExitFlag(false);
	}
}
