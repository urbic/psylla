package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _ne extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject obj2=opstack.pop();
		PsiObject obj1=opstack.pop();
		opstack.push(obj1.ne(obj2));
	}
}
