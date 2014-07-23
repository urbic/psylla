package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _type extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow");
			return;
		}
		PsiName result=new PsiName(opstack.pop().getTypeName()+"type");
		result.setExecutable();
		opstack.push(result);
	}
}
