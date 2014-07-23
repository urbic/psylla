package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _neg extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow");
			return;
		}
		
		PsiObject obj=opstack.pop();
		if(obj instanceof PsiNumeric)
			opstack.push(PsiNumeric.negate((PsiNumeric)obj));
		else
		{
			opstack.push(obj);
			interpreter.error("typecheck");
		}
	}
}
