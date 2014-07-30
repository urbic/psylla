package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _put extends PsiOperator
{
	@Override
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<3)
		{
			interpreter.error("stackunderflow", this);
			return;
		}
		PsiObject obj=opstack.pop();
		PsiObject key=opstack.pop();
		PsiObject indexed=opstack.pop();
		try
		{
			((PsiIndexed)indexed).psiPut(key, obj);
		}
		catch(ClassCastException e)
		{
			opstack.push(indexed);
			opstack.push(key);
			opstack.push(obj);
			interpreter.error("typecheck", this);
		}
		catch(PsiException e)
		{
			opstack.push(indexed);
			opstack.push(key);
			opstack.push(obj);
			interpreter.error(e.kind(), this);
		}
	}
}
