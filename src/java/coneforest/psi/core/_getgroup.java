package coneforest.psi.core;
import coneforest.psi.*;

public class _getgroup extends PsiOperator
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

		final PsiObject key=opstack.pop();
		final PsiObject matcher=opstack.pop();
		try
		{
			opstack.push(((PsiMatcher)matcher).psiGetGroup((PsiInteger)key));
		}
		catch(PsiException|ClassCastException e)
		{
			opstack.push(matcher);
			opstack.push(key);
			interpreter.error(e, this);
		}
	}
}