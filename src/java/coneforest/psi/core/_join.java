package coneforest.psi.core;
import coneforest.psi.*;

public final class _join extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		final PsiObject[] ops=ostack.popOperands(1);
		final PsiContext context=(PsiContext)ops[0];
		context.psiJoin();
		OperandStack joinedOpstack=((Interpreter)context).operandStack();
		ostack.push(PsiMark.MARK);
		for(PsiObject obj: joinedOpstack)
			ostack.push(obj);
	}
}
