package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _countdictstack extends PsiOperator
{
	@Override
	public void invoke(Interpreter interpreter)
	{
		interpreter.getOperandStack().push(new PsiInteger(interpreter.getDictionaryStack().size()));
	}
}
