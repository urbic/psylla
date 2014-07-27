package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _end extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		DictionaryStack dictstack=interpreter.getDictionaryStack();
		if(dictstack.size()<1)
			interpreter.error("dictstackunderflow", this);
		else
			dictstack.pop();
	}
}
