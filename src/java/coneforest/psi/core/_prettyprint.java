package coneforest.psi.core;
import coneforest.psi.*;

public class _prettyprint extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
			interpreter.error("stackunderflow", this);
		else
			System.out.println(opstack.pop());
	}
}
