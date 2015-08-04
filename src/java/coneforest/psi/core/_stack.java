package coneforest.psi.core;
import coneforest.psi.*;

public class _stack extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
	{
		final OperandStack opstack=interpreter.getOperandStack();
		PsiArray result=new PsiArray();
		for(PsiObject obj: opstack)
			result.psiAppend(obj);
		opstack.push(result);
	}
}
