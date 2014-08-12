package coneforest.psi.core;
import coneforest.psi.*;

public class _filereader extends PsiOperator
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

		PsiObject stringlike=opstack.pop();
		try
		{
			opstack.push(new PsiFileReader((PsiStringlike)stringlike));
		}
		catch(Exception e)
		{
			opstack.push(stringlike);
			interpreter.error(e, this);
		}
	}
}
