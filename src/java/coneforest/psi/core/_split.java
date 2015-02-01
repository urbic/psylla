package coneforest.psi.core;
import coneforest.psi.*;

public class _split extends PsiOperator
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

		final PsiObject regexp=opstack.pop();
		final PsiObject string=opstack.pop();
		try
		{
			opstack.push(((PsiString)string).psiSplit((PsiRegExp)regexp));
		}
		catch(ClassCastException e)
		{
			opstack.push(string);
			opstack.push(regexp);
			interpreter.error(e, this);
		}
	}
}
