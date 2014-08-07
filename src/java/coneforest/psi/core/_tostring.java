package coneforest.psi.core;
import coneforest.psi.*;

public class _tostring extends PsiOperator
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

		PsiObject convertable=opstack.pop();
		try
		{
			opstack.push(((PsiConvertableToString)convertable).psiToString());
		}
		catch(ClassCastException e)
		{
			opstack.push(convertable);
			interpreter.error("typecheck", this);
		}
	}
}