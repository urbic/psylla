package coneforest.psi.core;
import coneforest.psi.*;

public final class _read extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		PsiInteger character=((PsiReadable)ostack.popOperands(1)[0]).psiRead();
		boolean notEOF=(character!=PsiInteger.MINUS_ONE);
		if(notEOF)
			ostack.push(character);
		ostack.push(PsiBoolean.valueOf(notEOF));
	}
}
