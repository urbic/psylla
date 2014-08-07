package coneforest.psi.core;
import coneforest.psi.*;

public class _forall extends PsiOperator
{
	@Override
	public void invoke(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.error("stackunderflow", this);
			return;
		}
		PsiObject proc=opstack.pop();
		PsiObject iterable=opstack.pop();

		if(iterable instanceof PsiDictionarylike)
		{
			int looplevel=interpreter.pushLoopLevel();
			for(java.util.Map.Entry<String, PsiObject> entry:
						(PsiIterable<java.util.Map.Entry<String, PsiObject>>)iterable)
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
		else if(iterable instanceof PsiIterable)
		{
			//System.out.println("IT FORALL");
			int looplevel=interpreter.pushLoopLevel();
			for(PsiObject element: ((PsiIterable<? extends PsiObject>)iterable))
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
