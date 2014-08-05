package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _module extends PsiOperator
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
			opstack.push(interpreter.module(((PsiStringlike)stringlike).getString()));
		}
		catch(ClassCastException e)
		{
			opstack.push(stringlike);
			interpreter.error("typecheck", this);
		}
		catch(PsiException e)
		{
			opstack.push(stringlike);
			interpreter.error(e.kind(), this);
		}
	}
}
