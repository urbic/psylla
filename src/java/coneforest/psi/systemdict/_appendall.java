package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _appendall extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.error("stackunderflow");
			return;
		}

		PsiObject set2=opstack.pop();
		PsiObject set1=opstack.pop();

		try
		{
			((PsiSetlike)set1).appendAll((PsiSetlike)set2);
		}
		catch(ClassCastException e)
		{
			interpreter.error("typecheck");
		}
		catch(PsiException e)
		{
			interpreter.error(e.kind());
		}
	}
}
