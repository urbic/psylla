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

		PsiObject name=opstack.pop();
		try
		{
			opstack.push(new PsiFileWriter((PsiString)name));
		}
		catch(ClassCastException e)
		{
			interpreter.error("typecheck", this);
		}
		catch(PsiException e)
		{
			interpreter.error(e.kind(), this);
		}
	}
}
