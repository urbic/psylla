package coneforest.psi.core;
import coneforest.psi.*;

public class _exch extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		final OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		final PsiObject obj2=opstack.pop();
		final PsiObject obj1=opstack.pop();
		opstack.push(obj2);
		opstack.push(obj1);
	}
}
