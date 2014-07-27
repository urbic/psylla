package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _forall extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.error("stackunderflow", this);
			return;
		}
		PsiObject proc=opstack.pop();
		PsiObject obj=opstack.pop();

		if(obj instanceof PsiHashlike)
		{
			int looplevel=interpreter.pushLoopLevel();
			for(java.util.Map.Entry<String, PsiObject> entry: (PsiHashlike<PsiObject>)obj)
			{
				if(interpreter.getExitFlag())
					break;
				opstack.push(new PsiName(entry.getKey()));
				opstack.push(entry.getValue());
				proc.invoke(interpreter);
				interpreter.handleExecutionStack(looplevel);
			}
			interpreter.popLoopLevel();
			interpreter.setExitFlag(false);
		}
		else if(obj instanceof PsiIterable)
		{
			int looplevel=interpreter.pushLoopLevel();
			for(PsiObject element: ((PsiIterable<? extends PsiObject>)obj))
			{
				if(interpreter.getExitFlag())
					break;
				opstack.push(element);
				proc.invoke(interpreter);
				interpreter.handleExecutionStack(looplevel);
			}
			interpreter.popLoopLevel();
			interpreter.setExitFlag(false);
		}
		else
			interpreter.error("typecheck", this);
	}

}
