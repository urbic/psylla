package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _stringwriter extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();

		if(opstack.size()<1)
			interpreter.error("stackunderflow");

		PsiObject string=opstack.pop();
		if(string instanceof PsiString)
		{
			opstack.push(new PsiStringWriter((PsiString)string));
		}
		else
			interpreter.error("typecheck");
	}
}
