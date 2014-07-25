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

		try
		{

			int looplevel=interpreter.pushLoopLevel();
			// TODO: reverse
			for(
					PsiNumeric i=(PsiNumeric)initial;
					i.le((PsiNumeric)limit).getValue()
						&& !interpreter.getExitFlag();
					i=i.add((PsiNumeric)increment)
				)
			{
				opstack.push(i);
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
			interpreter.error("typecheck");
		}
	}
}
