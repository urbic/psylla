package coneforest.psi.core;
import coneforest.psi.*;

public class _length extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject composite=opstack.pop();
		try
		{
			opstack.push(((PsiLengthy)composite).psiLength());
		}
		catch(ClassCastException e)
		{
			opstack.push(composite);
			interpreter.error(e, this);
		}
	}
}
