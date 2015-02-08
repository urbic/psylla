package coneforest.psi.core;
import coneforest.psi.*;

public class _find extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		PsiMatcher matcher=new PsiMatcher((PsiStringlike)ops[0], (PsiRegExp)ops[1]);
		boolean resultValue=matcher.psiFind().booleanValue();
		if(resultValue)
			opstack.push(matcher);
		opstack.push(PsiBoolean.valueOf(resultValue));
	}
}
