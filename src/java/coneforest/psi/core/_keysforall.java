package coneforest.psi.core;
import coneforest.psi.*;

public class _keysforall extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		final OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.handleError("stackunderflow", this);
			return;
		}

		final PsiObject proc=opstack.pop();
		final PsiObject dictionarylike=opstack.pop();
		try
		{
			int looplevel=interpreter.pushLoopLevel();
			for(java.util.Map.Entry<String, PsiObject> entry:
						(PsiDictionary)dictionarylike)
			{
				if(interpreter.getExitFlag())
					break;
				opstack.push(new PsiName(entry.getKey()));
				proc.invoke(interpreter);
				interpreter.handleExecutionStack(looplevel);
			}
			interpreter.popLoopLevel();
			interpreter.setExitFlag(false);
		}
		catch(ClassCastException e)
		{
			opstack.push(dictionarylike);
			opstack.push(proc);
			interpreter.handleError(e, this);
		}
	}
}
