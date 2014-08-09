package coneforest.psi.core;
import coneforest.psi.*;

public class _say extends PsiOperator
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
			PsiWriter stdwriter=(PsiWriter)interpreter.getSystemDictionary().psiGet("stdout");
			stdwriter.psiWriteString((PsiStringlike)stringlike);
			stdwriter.psiWrite(new PsiInteger(10));
			stdwriter.psiFlush();
		}
		catch(Exception e)
		{
			opstack.push(stringlike);
			interpreter.error(e, this);
		}
	}
}
