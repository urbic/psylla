package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _prettyprint extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow");
			return;
		}
		else
			System.out.println(opstack.pop());
	}
}
