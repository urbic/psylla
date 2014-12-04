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

		final PsiObject stringlike=opstack.pop();
		final PsiObject regexp=opstack.pop();
		try
		{
			opstack.push(((PsiRegExp)regexp).psiSplit((PsiStringlike)stringlike));
		}
		catch(ClassCastException e)
		{
			opstack.push(regexp);
			opstack.push(stringlike);
			interpreter.error(e, this);
		}
	}
}
