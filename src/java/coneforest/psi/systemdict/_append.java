package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _append extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject obj=opstack.pop();
		PsiObject set=opstack.pop();
		try
		{
			((PsiSetlike)set).append(obj);
		}
		catch(ClassCastException e)
		{
			opstack.push(set);
			opstack.push(obj);
			interpreter.error("typecheck", this);
		}
		catch(PsiException e)
		{
			opstack.push(set);
			opstack.push(obj);
			interpreter.error(e.kind(), this);
		}
	}
}
