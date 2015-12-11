package coneforest.psi.core;
import coneforest.psi.*;

public final class _index extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		int nValue=((PsiInteger)ostack.popOperands(1)[0]).intValue();
		if(nValue<0)
			throw new PsiRangeCheckException();
		ostack.ensureSize(nValue+1);
		ostack.push(ostack.get(ostack.size()-nValue-1));
	}
}
