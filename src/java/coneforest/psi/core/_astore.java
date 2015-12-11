package coneforest.psi.core;
import coneforest.psi.*;

public final class _astore extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		int countValue=((PsiInteger)ostack.popOperands(1)[0]).intValue();
		ostack.ensureSize(countValue);
		PsiArray array=new PsiArray();
		while(--countValue>=0)
			((PsiArray)array).psiAppend(ostack.pop());
		ostack.push(array);
	}
}
