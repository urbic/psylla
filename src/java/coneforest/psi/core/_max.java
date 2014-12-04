package coneforest.psi.core;
import coneforest.psi.*;

public class _max extends PsiOperator
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

		final PsiObject scalar2=opstack.pop();
		final PsiObject scalar1=opstack.pop();
		try
		{
			opstack.push(((PsiScalar)scalar1).psiGt((PsiScalar)scalar2).booleanValue()? scalar1: scalar2);
		}
		catch(ClassCastException e)
		{
			opstack.push(scalar1);
			opstack.push(scalar2);
			interpreter.error(e, this);
		}
	}
}
