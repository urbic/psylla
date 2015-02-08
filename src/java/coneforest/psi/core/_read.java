package coneforest.psi.core;
import coneforest.psi.*;

public class _read extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		PsiInteger character=((PsiReadable)opstack.popOperands(1)[0]).psiRead();
		boolean notEOF=(character.intValue()!=-1);
		if(notEOF)
			opstack.push(character);
		opstack.push(PsiBoolean.valueOf(notEOF));
	}
}
