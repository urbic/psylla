package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _floor extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow", this);
			return;
		}
		
		PsiObject numeric=opstack.pop();
		try
		{
			opstack.push(((PsiNumeric)numeric).floor());
		}
		catch(ClassCastException e)
		{
			opstack.push(numeric);
			interpreter.error("typecheck", this);
		}
	}
}
