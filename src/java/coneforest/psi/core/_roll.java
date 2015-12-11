package coneforest.psi.core;
import coneforest.psi.*;

public final class _roll extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		final PsiObject[] ops=ostack.popOperands(2);
		int nValue=((PsiInteger)ops[0]).intValue();
		int jValue=((PsiInteger)ops[1]).intValue();
		int ostackSize=ostack.size();
		if(nValue<0)
			throw new PsiRangeCheckException();
		if(nValue==0)
			return;
		ostack.ensureSize(nValue);
		while(jValue<0)
			jValue+=nValue;
		jValue%=nValue;
		for(int i=0; i<jValue; i++)
			ostack.add(ostackSize-nValue, ostack.pop());
	}
}
