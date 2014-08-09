package coneforest.psi.core;
import coneforest.psi.*;

public class _filewriter extends PsiOperator
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
			opstack.push(new PsiFileWriter((PsiStringlike)stringlike));
		}
		catch(Exception e)
		{
			opstack.push(stringlike);
			interpreter.error(e, this);
		}
	}
}
