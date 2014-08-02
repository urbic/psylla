package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _reverse extends PsiOperator
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

		PsiObject arraylike=opstack.pop();
		try
		{
			((PsiArraylike)arraylike).psiReverse();
		}
		catch(ClassCastException e)
		{
			opstack.push(arraylike);
			interpreter.error("typecheck", this);
		}
		catch(PsiException e)
		{
			opstack.push(arraylike);
			interpreter.error(e.kind(), this);
		}
	}
}
