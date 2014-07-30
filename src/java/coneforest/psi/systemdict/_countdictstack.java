package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _countdictstack extends PsiOperator
{
	@Override
	public void execute(Interpreter interpreter)
	{
		interpreter.getOperandStack().push(new PsiInteger(interpreter.getDictionaryStack().size()));
	}
}
