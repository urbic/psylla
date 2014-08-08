package coneforest.psi.core;
import coneforest.psi.*;

public class _literal extends PsiOperator
{
	@Override
	public void invoke(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
			interpreter.error("stackunderflow", this);
		else
			opstack.peek().setLiteral();
	}
}
