package coneforest.psi.core;
import coneforest.psi.*;

public class _astore extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		int countValue=((PsiInteger)opstack.popOperands(1)[0]).intValue();
		opstack.ensureSize(countValue);
		PsiArray array=new PsiArray();
		while(--countValue>=0)
			((PsiArray)array).psiAppend(opstack.pop());
		opstack.push(array);
	}
}
