package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _exch extends PsiOperator
{
	@Override
	public void invoke(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject obj2=opstack.pop();
		PsiObject obj1=opstack.pop();
		opstack.push(obj2);
		opstack.push(obj1);
	}
}
