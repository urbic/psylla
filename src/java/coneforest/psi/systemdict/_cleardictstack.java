package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _cleardictstack extends PsiOperator
{
	@Override
	public void execute(Interpreter interpreter)
	{
		interpreter.getDictionaryStack().setSize(3);
	}
}
