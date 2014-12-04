package coneforest.psi.core;
import coneforest.psi.*;

public class _isempty extends PsiOperator
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

		final PsiObject composite=opstack.pop();
		try
		{
			opstack.push(((PsiLengthy)composite).psiIsEmpty());
		}
		catch(ClassCastException e)
		{
			opstack.push(composite);
			interpreter.error(e, this);
		}
	}
}
