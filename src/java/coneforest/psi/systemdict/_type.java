package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _type extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
			interpreter.error("stackunderflow");
		else
		{
			PsiObject obj=opstack.pop();
			PsiName result=new PsiName(obj.getTypeName()+"type");
			result.setExecutable();
			opstack.push(result);
		}
	}
}
