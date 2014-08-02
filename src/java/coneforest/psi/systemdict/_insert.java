package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _insert extends PsiOperator
{
	@Override
	public void invoke(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<3)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject obj=opstack.pop();
		PsiObject index=opstack.pop();
		PsiObject arraylike=opstack.pop();
		try
		{
			((PsiArraylike)arraylike).psiInsert((PsiInteger)index, obj);
		}
		catch(ClassCastException e)
		{
			opstack.push(arraylike);
			opstack.push(index);
			opstack.push(obj);
			interpreter.error("typecheck", this);
		}
		catch(PsiException e)
		{
			opstack.push(arraylike);
			opstack.push(index);
			opstack.push(obj);
			interpreter.error(e.kind(), this);
		}
	}
}
