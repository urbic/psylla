package coneforest.psi.core;
import coneforest.psi.*;

public class _roll extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		int nValue=((PsiInteger)ops[0]).intValue();
		int jValue=((PsiInteger)ops[1]).intValue();
		int opstackSize=opstack.size();
		if(nValue<0)
			throw new PsiException("rangecheck");
		if(nValue==0)
			return;
		opstack.ensureSize(nValue);
		while(jValue<0)
			jValue+=nValue;
		jValue%=nValue;
		for(int i=0; i<jValue; i++)
			opstack.add(opstackSize-nValue, opstack.pop());
	}
}
