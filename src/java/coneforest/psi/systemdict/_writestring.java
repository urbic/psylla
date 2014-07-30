package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _writestring extends PsiOperator
{
	@Override
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject string=opstack.pop();
		PsiObject writer=opstack.pop();
		try
		{
			((PsiWriter)writer).psiWriteString((PsiString)string);
		}
		catch(ClassCastException e)
		{
			opstack.push(writer);
			opstack.push(string);
			interpreter.error("typecheck", this);
		}
		catch(PsiException e)
		{
			opstack.push(writer);
			opstack.push(string);
			interpreter.error(e.kind(), this);
		}
	}
}
