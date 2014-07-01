package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _cvn extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow");
			return;
		}
		PsiObject obj=opstack.pop();
		if(obj instanceof PsiString)
		{
			opstack.push(new PsiName(((PsiString)obj).getValue()));
		}
		else
			interpreter.error("typecheck");
	}
}
