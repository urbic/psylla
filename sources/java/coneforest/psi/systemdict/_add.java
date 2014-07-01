package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _add extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.error("stackunderflow");
			return;
		}
		PsiObject y=opstack.pop();
		PsiObject x=opstack.pop();

		if(x instanceof PsiNumeric && y instanceof PsiNumeric)
			opstack.push(PsiNumeric.sum((PsiNumeric)x, (PsiNumeric)y));
		else
		{
			opstack.push(x);
			opstack.push(y);
			interpreter.error("typecheck");
		}
	}
}
