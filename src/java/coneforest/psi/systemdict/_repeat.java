package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _repeat extends PsiOperator
{
	@Override
	public void execute(Interpreter interpreter)
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
			long countValue=((PsiInteger)count).getValue();
			if(countValue<0)
			{
				// TODO
				interpreter.error("rangecheck", this);
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
		catch(ClassCastException e)
		{
			// TODO
			interpreter.error("typecheck", this);
		}
	}
}
