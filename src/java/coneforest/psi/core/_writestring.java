package coneforest.psi.core;
import coneforest.psi.*;

public class _writestring extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		final OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.handleError("stackunderflow", this);
			return;
		}

		final PsiObject stringlike=opstack.pop();
		final PsiObject writer=opstack.pop();
		try
		{
			((PsiWriter)writer).psiWriteString((PsiStringlike)stringlike);
		}
		catch(ClassCastException|PsiException e)
		{
			opstack.push(writer);
			opstack.push(stringlike);
			interpreter.handleError(e, this);
		}
	}
}
