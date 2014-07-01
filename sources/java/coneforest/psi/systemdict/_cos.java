package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _cos extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()==0)
			interpreter.error("stackunderflow");
		else
		{
			PsiObject obj=opstack.pop();
			if(obj instanceof PsiNumeric)
				opstack.push(PsiNumeric.cos((PsiNumeric)obj));
			else
			{
				opstack.push(obj);
				interpreter.error("typecheck");
			}
		}
	}
}
