package coneforest.psi.core;
import coneforest.psi.*;

public class _sinh extends PsiOperator
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

		final PsiObject cn=opstack.pop();
		try
		{
			opstack.push(((PsiComplexNumeric)cn).psiSinh());
		}
		catch(ClassCastException e)
		{
			opstack.push(cn);
			interpreter.error(e, this);
		}
	}
}
