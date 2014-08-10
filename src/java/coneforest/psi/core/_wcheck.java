package coneforest.psi.core;
import coneforest.psi.*;

public class _wcheck extends PsiOperator
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

		PsiObject lengthy=opstack.pop();
		try
		{
			opstack.push(new PsiBoolean(((PsiLengthy)lengthy).isWritable()));
		}
		catch(Exception e)
		{
			opstack.push(lengthy);
			interpreter.error(e, this);
		}
	}
}
