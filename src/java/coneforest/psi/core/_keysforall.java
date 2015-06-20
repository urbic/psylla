package coneforest.psi.core;
import coneforest.psi.*;

public class _keysforall extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		final PsiDictionarylike dictlike=(PsiDictionarylike)ops[0];
		final PsiProcedure proc=(PsiProcedure)ops[1];

		final int looplevel=interpreter.pushLoopLevel();
		for(java.util.Map.Entry<String, PsiObject> entry:
				(PsiIterable<java.util.Map.Entry<String, PsiObject>>)dictlike)
		{
			opstack.push(new PsiName(entry.getKey()));
			proc.invoke(interpreter);
			interpreter.handleExecutionStack(looplevel);
			if(interpreter.getStopFlag() || interpreter.getExitFlag())
				break;
		}
		interpreter.popLoopLevel();
		interpreter.setExitFlag(false);
	}
}
