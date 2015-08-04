package coneforest.psi.core;
import coneforest.psi.*;

public class _dictstack extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final DictStack dictstack=interpreter.getDictStack();
		PsiArray result=new PsiArray();
		for(PsiObject obj: dictstack)
			result.psiAppend(obj);
		opstack.push(result);
	}
}
