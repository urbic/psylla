package coneforest.psi.core;
import coneforest.psi.*;

public class _find extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		final OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.handleError("stackunderflow", this);
			return;
		}

		final PsiObject regexp=opstack.pop();
		final PsiObject stringlike=opstack.pop();
		try
		{
			PsiMatcher matcher=new PsiMatcher((PsiStringlike)stringlike, (PsiRegExp)regexp);
			boolean resultValue=matcher.psiFind().booleanValue();
			if(resultValue)
				opstack.push(matcher);
			opstack.push(new PsiBoolean(resultValue));
		}
		catch(ClassCastException e)
		{
			opstack.push(stringlike);
			opstack.push(regexp);
			interpreter.handleError(e, this);
		}
	}
}
