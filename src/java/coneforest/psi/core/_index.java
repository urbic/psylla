package coneforest.psi.core;
import coneforest.psi.*;

public class _index extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		int nValue=((PsiInteger)opstack.popOperands(1)[0]).intValue();
		if(nValue<0)
			throw new PsiException("rangecheck");
		opstack.ensureSize(nValue+1);
		opstack.push(opstack.get(opstack.size()-nValue-1));
	}
}
