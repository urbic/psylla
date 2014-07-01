package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _log extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
			interpreter.error("stackunderflow");
		else
		{
			PsiObject obj=opstack.pop();
			if(obj instanceof PsiNumeric)
			{
				PsiReal result=PsiNumeric.log((PsiNumeric)obj);
				if(result.getValue().isNaN() || result.getValue().isInfinite())
					interpreter.error("rangecheck");
				else
					opstack.push(result);
			}
			else
				interpreter.error("typecheck");
		}
	}
}
