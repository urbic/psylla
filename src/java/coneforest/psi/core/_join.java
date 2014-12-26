package coneforest.psi.core;
import coneforest.psi.*;

public class _join extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		final OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		final PsiObject arraylike=opstack.pop();
		final PsiObject string=opstack.pop();
		try
		{
			opstack.push(((PsiString)string).psiJoin((PsiArraylike)arraylike));
		}
		catch(ClassCastException|PsiException e)
		{
			opstack.push(string);
			opstack.push(arraylike);
			interpreter.error(e, this);
		}
	}
}
