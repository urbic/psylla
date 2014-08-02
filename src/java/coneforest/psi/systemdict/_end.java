package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _end extends PsiOperator
{
	@Override
	public void invoke(Interpreter interpreter)
	{
		DictionaryStack dictstack=interpreter.getDictionaryStack();
		if(dictstack.size()<=3)
			interpreter.error("dictstackunderflow", this);
		else
			dictstack.pop();
	}
}
