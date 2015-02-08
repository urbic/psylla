package coneforest.psi.core;
import coneforest.psi.*;

public class _for extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(4);
		final PsiNumeric initial=(PsiNumeric)ops[0];
		final PsiNumeric increment=(PsiNumeric)ops[1];
		final PsiNumeric limit=(PsiNumeric)ops[2];
		final PsiObject obj=ops[3];

		final int loopLevel=interpreter.pushLoopLevel();
		final boolean forward=increment.psiGt(PsiInteger.ZERO).booleanValue();
		for(
				PsiNumeric counter=initial;
				(forward && counter.psiLe(limit).booleanValue()
						|| !forward && counter.psiGe(limit).booleanValue());
				counter=(PsiNumeric)counter.psiAdd(increment)
			)
		{
			opstack.push(counter);
			obj.invoke(interpreter);
			interpreter.handleExecutionStack(loopLevel);
		}
		interpreter.popLoopLevel();
		interpreter.setExitFlag(false);
	}
}
