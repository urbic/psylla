package coneforest.psi.core;
import coneforest.psi.*;

public final class _copy extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		final int n=((PsiInteger)ostack.popOperands(1)[0]).intValue();
		if(n<0)
			throw new PsiRangeCheckException();
		ostack.ensureSize(n);
		final int opsize=ostack.size();
		for(int j=opsize-n; j<opsize; j++)
			ostack.push(ostack.get(j));
	}
}
