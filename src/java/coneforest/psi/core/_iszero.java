package coneforest.psi.core;
import coneforest.psi.*;

public class _iszero extends PsiOperator
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
			opstack.push(((PsiComplexNumeric)cn).psiIsZero());
		}
		catch(ClassCastException e)
		{
			opstack.push(cn);
			interpreter.error(e, this);
		}
	}
}
