package coneforest.psi.core;
import coneforest.psi.*;

public class _stringwriter extends PsiOperator
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

		PsiObject string=opstack.pop();
		try
		{
			opstack.push(new PsiStringWriter((PsiString)string));
		}
		catch(ClassCastException e)
		{
			opstack.push(string);
			interpreter.error("typecheck", this);
		}
	}
}
