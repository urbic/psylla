package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _cvs extends PsiOperator
{
	@Override
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow", this);
			return;
		}
		PsiObject obj=opstack.pop();
		opstack.push(new PsiString(obj.toString()));
	}
}
