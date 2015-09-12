package coneforest.psi.core;
import coneforest.psi.*;

public final class _join extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(1);
		final PsiContext context=(PsiContext)ops[0];
		context.psiJoin();
		OperandStack joinedOpstack=((Interpreter)context).getOperandStack();
		opstack.push(PsiMark.MARK);
		for(PsiObject obj: joinedOpstack)
			opstack.push(obj);
	}
}
