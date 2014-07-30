package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _prettyprint extends PsiOperator
{
	@Override
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
			interpreter.error("stackunderflow", this);
		else
			System.out.println(opstack.pop());
	}
}
