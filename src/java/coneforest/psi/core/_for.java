package coneforest.psi.core;
import coneforest.psi.*;

public class _for extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		final OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<4)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		final PsiObject obj=opstack.pop();
		final PsiObject limit_=opstack.pop();
		final PsiObject increment_=opstack.pop();
		final PsiObject initial_=opstack.pop();

		try
		{
			final PsiNumeric initial=(PsiNumeric)initial_;
			final PsiNumeric increment=(PsiNumeric)increment_;
			final PsiNumeric limit=(PsiNumeric)limit_;

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
		catch(ClassCastException e)
		{
			opstack.push(initial_);
			opstack.push(increment_);
			opstack.push(limit_);
			opstack.push(obj);
			interpreter.error(e, this);
		}
	}
}
