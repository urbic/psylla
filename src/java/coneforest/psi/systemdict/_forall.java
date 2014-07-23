package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _forall extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.error("stackunderflow");
			return;
		}
		PsiObject proc=opstack.pop();
		PsiObject obj=opstack.pop();

		if(obj instanceof PsiArray)
		{
			int looplevel=interpreter.pushLoopLevel();
			for(PsiObject element: (PsiArray)obj)
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
		else if(obj instanceof PsiDictionary)
		{
			int looplevel=interpreter.pushLoopLevel();
			for(java.util.Map.Entry<String, PsiObject> entry: ((PsiDictionary)obj).entrySet())
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
		else if(obj instanceof PsiBitVector)
		{
			int looplevel=interpreter.pushLoopLevel();
			for(PsiBoolean element: (PsiBitVector)obj)
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
			interpreter.error("typecheck");
	}
}
