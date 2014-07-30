package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _removeall extends PsiOperator
{
	@Override
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject iterable=opstack.pop();
		PsiObject setlike=opstack.pop();
		try
		{
			((PsiSetlike)setlike).psiRemoveAll((PsiIterable)iterable);
		}
		catch(ClassCastException e)
		{
			opstack.push(setlike);
			opstack.push(iterable);
			interpreter.error("typecheck", this);
		}
		catch(PsiException e)
		{
			opstack.push(setlike);
			opstack.push(iterable);
			interpreter.error(e.kind(), this);
		}
	}
}
