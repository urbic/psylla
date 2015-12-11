package coneforest.psi.core;
import coneforest.psi.*;

public final class _readline extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		PsiStringy eol=(PsiStringy)interpreter.dictStack().load("eol");
		PsiString string=((PsiReadable)ostack.popOperands(1)[0]).psiReadLine(eol);
		if(string.length()>0)
			ostack.push(string);
		ostack.push(PsiBoolean.valueOf(string.length()>0));
	}
}
