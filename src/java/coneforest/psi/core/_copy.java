package coneforest.psi.core;
import coneforest.psi.*;

public class _copy extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		int nValue=((PsiInteger)opstack.popOperands(1)[0]).intValue();
		if(nValue<0)
			throw new PsiRangeCheckException();
		opstack.ensureSize(nValue);
		int opsize=opstack.size();
		for(int j=opsize-nValue; j<opsize; j++)
			opstack.push(opstack.get(j));
	}
}
