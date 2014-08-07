package coneforest.psi.core;
import coneforest.psi.*;

public class _load extends PsiOperator
{
	@Override
	public void invoke(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject key=opstack.pop();
		try
		{
			opstack.push(interpreter.getDictionaryStack().load((PsiStringlike)key));
		}
		catch(ClassCastException e)
		{
			opstack.push(key);
			interpreter.error("typecheck", this);
		}
		catch(PsiException e)
		{
			opstack.push(key);
			interpreter.error(e.kind(), this);
		}
	}
}