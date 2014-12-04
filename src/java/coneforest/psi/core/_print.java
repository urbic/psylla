package coneforest.psi.core;
import coneforest.psi.*;

public class _print extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		final OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		final PsiObject string=opstack.pop();
		try
		{
			((PsiWriter)interpreter.getSystemDictionary().psiGet("stdout"))
				.psiWriteString((PsiString)string);
		}
		catch(ClassCastException|PsiException e)
		{
			opstack.push(string);
			interpreter.error(e, this);
		}
	}
}
