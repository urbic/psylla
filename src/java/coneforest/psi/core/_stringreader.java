package coneforest.psi.core;
import coneforest.psi.*;

public class _stringreader extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
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
			opstack.push(new PsiStringReader((PsiStringlike)stringlike));
		}
		catch(ClassCastException e)
		{
			opstack.push(stringlike);
			interpreter.error(e, this);
		}
	}
}
