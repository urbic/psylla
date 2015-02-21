package coneforest.psi.core;
import coneforest.psi.*;

public class _end extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws PsiException
	{
		DictionaryStack dictstack=interpreter.getDictionaryStack();
		if(dictstack.size()<=2)
			throw new PsiException("dictstackunderflow");
		dictstack.pop();
	}
}
