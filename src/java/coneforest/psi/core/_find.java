package coneforest.psi.core;
import coneforest.psi.*;

public final class _find extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		/*
		final PsiObject[] ops=ostack.popOperands(2);
		PsiMatcher matcher=new PsiMatcher((PsiStringy)ops[0], (PsiRegExp)ops[1]);
		boolean resultValue=matcher.psiFind().booleanValue();
		if(resultValue)
			ostack.push(matcher);
		ostack.push(PsiBoolean.valueOf(resultValue));
		*/
		PsiMatcher matcher=(PsiMatcher)ostack.popOperands(1)[0];
		boolean resultValue=matcher.psiFind().booleanValue();
		if(resultValue)
			ostack.push(matcher);
		ostack.push(PsiBoolean.valueOf(resultValue));
	}
}
