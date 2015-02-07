package coneforest.psi.core;
import coneforest.psi.*;

public class _println extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		final OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.handleError("stackunderflow", this);
			return;
		}

		final PsiObject stringlike=opstack.pop();
		try
		{
			PsiWriter stdwriter=(PsiWriter)interpreter.getDictionaryStack().load("stdout");
			stdwriter.psiWriteString((PsiStringlike)stringlike);
			stdwriter.psiWriteString((PsiStringlike)interpreter.getDictionaryStack().load("eol"));
		}
		catch(ClassCastException|PsiException e)
		{
			opstack.push(stringlike);
			interpreter.handleError(e, this);
		}
	}
}
