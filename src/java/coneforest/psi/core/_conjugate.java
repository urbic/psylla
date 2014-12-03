package coneforest.psi.core;
import coneforest.psi.*;

public class _conjugate extends PsiOperator
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

		PsiObject cn=opstack.pop();
		try
		{
			opstack.push(((PsiComplexNumeric)cn).psiConjugate());
		}
		catch(ClassCastException e)
		{
			opstack.push(cn);
			interpreter.error(e, this);
		}
	}
}
