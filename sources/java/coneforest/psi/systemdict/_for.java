package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _for extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<4)
		{
			interpreter.error("stackunderflow");
			return;
		}
		PsiObject obj=opstack.pop();
		PsiObject limit=opstack.pop();
		PsiObject increment=opstack.pop();
		PsiObject initial=opstack.pop();

		if(limit instanceof PsiNumeric
				&& increment instanceof PsiNumeric
				&& initial instanceof PsiNumeric)
		{

			interpreter.pushLoopLevel();
			// TODO: reverse
			for(PsiNumeric i=(PsiNumeric)initial;
					PsiNumeric.le(i, (PsiNumeric)limit).getValue() && !interpreter.getExitFlag();
					i=PsiNumeric.sum(i, (PsiNumeric)increment))
			{
				opstack.push(i);
					//int level=interpreter.getExecutionStack().size();
				//int currentLoopLevel=interpreter.getLoopLevel();
				obj.invoke(interpreter);
				//System.out.println("UUU "+currentLoopLevel);
				interpreter.handleExecutionStack(interpreter.currentLoopLevel());
				//interpreter.setLoopLevel(currentLoopLevel);
			}
			interpreter.setExitFlag(false);

		}
		else
			interpreter.error("typecheck");
	}
}
