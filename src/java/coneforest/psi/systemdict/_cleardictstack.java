package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _cleardictstack extends PsiOperator
{
	@Override
	public void invoke(Interpreter interpreter)
	{
		interpreter.getDictionaryStack().setSize(3);
	}
}
