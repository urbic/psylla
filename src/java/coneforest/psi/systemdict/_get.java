package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _get extends PsiOperator
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

		PsiObject key=opstack.pop();
		PsiObject indexed=opstack.pop();
		try
		{
			opstack.push(((PsiIndexed)indexed).psiGet(key));
		}
		catch(ClassCastException e)
		{
			opstack.push(indexed);
			opstack.push(key);
			interpreter.error("typecheck", this);
		}
		catch(PsiException e)
		{
			opstack.push(indexed);
			opstack.push(key);
			interpreter.error(e.kind(), this);
		}
	}
}
