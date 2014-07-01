package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _repeat extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.error("stackunderflow");
			return;
		}
		PsiObject obj=opstack.pop();
		PsiObject count=opstack.pop();

		if(count instanceof PsiInteger)
		{
			long countValue=((PsiInteger)count).getValue();
			if(countValue<0)
			{
				interpreter.error("rangecheck");
				return;
			}
			int looplevel=interpreter.pushLoopLevel();
			// TODO: reverse
			for(int i=0;
					i<countValue && !interpreter.getExitFlag();
					i++)
			{
				obj.invoke(interpreter);
				interpreter.handleExecutionStack(looplevel);
			}
			interpreter.setExitFlag(false);

		}
		else
			interpreter.error("typecheck");
	}
}
