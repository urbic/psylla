package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _bitset extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		interpreter.getOperandStack().push(new PsiBitSet());
	}
}
