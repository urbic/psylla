package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _cvx extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()==0)
			interpreter.error("stackunderflow");
		else
			opstack.peek().setExecutable();
	}
}
