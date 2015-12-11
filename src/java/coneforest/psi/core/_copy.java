package coneforest.psi.core;
import coneforest.psi.*;

public final class _copy extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		int nValue=((PsiInteger)ostack.popOperands(1)[0]).intValue();
		if(nValue<0)
			throw new PsiRangeCheckException();
		ostack.ensureSize(nValue);
		int opsize=ostack.size();
		for(int j=opsize-nValue; j<opsize; j++)
			ostack.push(ostack.get(j));
	}
}
