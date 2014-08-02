package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _cbrt extends PsiOperator
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

		PsiObject numeric=opstack.pop();
		try
		{
			opstack.push(((PsiNumeric)numeric).cbrt());
		}
		catch(ClassCastException e)
		{
			interpreter.error("typecheck", this);
		}
	}
}
