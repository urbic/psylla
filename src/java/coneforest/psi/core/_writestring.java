package coneforest.psi.core;
import coneforest.psi.*;

public class _writestring extends PsiOperator
{
	@Override
	public void invoke(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject stringlike=opstack.pop();
		PsiObject writer=opstack.pop();
		try
		{
			((PsiWriter)writer).psiWriteString((PsiStringlike)stringlike);
		}
		catch(ClassCastException e)
		{
			opstack.push(writer);
			opstack.push(stringlike);
			interpreter.error("typecheck", this);
		}
		catch(PsiException e)
		{
			opstack.push(writer);
			opstack.push(stringlike);
			interpreter.error(e.kind(), this);
		}
	}
}
