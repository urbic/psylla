package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _currentdict extends PsiOperator
{
	@Override
	public void execute(Interpreter interpreter)
	{
		interpreter.getOperandStack().push(interpreter.getCurrentDictionary());
	}
}
