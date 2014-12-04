package coneforest.psi.core;
import coneforest.psi.*;

public class _clone extends PsiOperator
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
		opstack.push(opstack.pop().psiClone());
	}
}
