package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _exch extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
			interpreter.error("stackunderflow");
		else
		{
			PsiObject obj1=opstack.pop();
			PsiObject obj2=opstack.pop();
			opstack.push(obj1);
			opstack.push(obj2);
		}
	}
}
