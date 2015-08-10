package coneforest.psi.core;
import coneforest.psi.*;

public class _end extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws PsiException
	{
		DictStack dictstack=interpreter.getDictStack();
		if(dictstack.size()<=2)
			throw new PsiDictStackUnderflowException();
		dictstack.pop();
	}
}
