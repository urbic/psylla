package coneforest.psi.core;
import coneforest.psi.*;

public class _repeat extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject obj=opstack.pop();
		PsiObject count=opstack.pop();
		try
		{
			long countValue=((PsiInteger)count).longValue();
			if(countValue<0)
				throw new PsiException("rangecheck");
			int looplevel=interpreter.pushLoopLevel();
			for(int i=0;
					i<countValue && !interpreter.getExitFlag();
					i++)
			{
				obj.invoke(interpreter);
				interpreter.handleExecutionStack(looplevel);
			}
			interpreter.popLoopLevel();
			interpreter.setExitFlag(false);

		}
		catch(ClassCastException|PsiException e)
		{
			opstack.push(count);
			opstack.push(obj);
			interpreter.error(e, this);
		}
	}
}
