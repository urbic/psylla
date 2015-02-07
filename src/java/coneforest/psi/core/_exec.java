package coneforest.psi.core;
import coneforest.psi.*;

public class _exec extends PsiOperator
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

		opstack.pop().invoke(interpreter);
	}
}
