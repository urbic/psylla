package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _for extends PsiOperator
{
	@Override
	public void invoke(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<4)
		{
			interpreter.error("stackunderflow", this);
			return;
		}
		PsiObject obj=opstack.pop();
		PsiObject limit=opstack.pop();
		PsiObject increment=opstack.pop();
		PsiObject initial=opstack.pop();

		try
		{

			int looplevel=interpreter.pushLoopLevel();
			// TODO: reverse
			for(
					PsiNumeric counter=(PsiNumeric)initial;
					counter.psiLe((PsiNumeric)limit).getValue()
						&& !interpreter.getExitFlag();
					counter=(PsiNumeric)counter.psiAdd((PsiNumeric)increment)
				)
			{
				opstack.push(counter);
					//int level=interpreter.getExecutionStack().size();
				//int currentLoopLevel=interpreter.getLoopLevel();
				obj.invoke(interpreter);
				//System.out.println("UUU "+currentLoopLevel);
				interpreter.handleExecutionStack(looplevel);
				//interpreter.setLoopLevel(currentLoopLevel);
			}
			interpreter.popLoopLevel();
			interpreter.setExitFlag(false);

		}
		catch(ClassCastException e)
		{
			interpreter.error("typecheck", this);
		}
	}
}
