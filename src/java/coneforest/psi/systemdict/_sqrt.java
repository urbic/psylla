package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _sqrt extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
			interpreter.error("stackunderflow");
		else
		{
			PsiObject obj=opstack.peek();
			if(obj instanceof PsiNumeric)
			{
				PsiReal result=PsiNumeric.sqrt((PsiNumeric)obj);
				if(result.getValue().isNaN())
					interpreter.error("rangecheck");
				else
					opstack.push(result);
			}
			else
				interpreter.error("typecheck");
		}
	}
}