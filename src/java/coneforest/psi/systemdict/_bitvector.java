package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _bitvector extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		interpreter.getOperandStack().push(new PsiBitVector());
	}
}
