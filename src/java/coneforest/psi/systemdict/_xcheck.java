package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _xcheck extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow");
			return;
		}
		opstack.push(new PsiBoolean(opstack.pop().isExecutable()));
	}
}