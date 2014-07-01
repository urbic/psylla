package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _cvi extends PsiOperator
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

		if(obj instanceof PsiInteger)
		{
			opstack.push(obj);
		}
		else if(obj instanceof PsiReal)
		{
			double objValue=((PsiReal)obj).getValue();
			if(objValue>=Long.MIN_VALUE && objValue<=Long.MAX_VALUE)
				opstack.push(new PsiInteger(((PsiReal)obj).getValue().intValue()));
			else
				interpreter.error("rangecheck");
		}
		else if(obj instanceof PsiString)
		{
			try
			{
				opstack.push(new PsiInteger(new Long(((PsiString)obj).getValue())));
			}
			catch(NumberFormatException e)
			{
				interpreter.error("syntaxerror");
			}
		}
		else
			interpreter.error("typecheck");
	}
}
