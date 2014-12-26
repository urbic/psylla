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

		final PsiObject string=opstack.pop();
		final PsiObject regexp=opstack.pop();
		try
		{
			opstack.push(((PsiRegExp)regexp).psiSplit((PsiString)string));
		}
		catch(ClassCastException e)
		{
			opstack.push(regexp);
			opstack.push(string);
			interpreter.error(e, this);
		}
	}
}
