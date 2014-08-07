package coneforest.psi.core;
import coneforest.psi.*;

public class _type extends PsiOperator
{
	@Override
	public void invoke(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiName result=new PsiName(opstack.pop().getTypeName()+"type");
		result.setExecutable();
		opstack.push(result);
	}
}
