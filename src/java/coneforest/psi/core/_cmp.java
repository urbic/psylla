package coneforest.psi.core;
import coneforest.psi.*;

public class _cmp extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		final OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.handleError("stackunderflow", this);
			return;
		}

		final PsiObject scalar2=opstack.pop();
		final PsiObject scalar1=opstack.pop();
		try
		{
			opstack.push(((PsiScalar)scalar1).psiCmp((PsiScalar)scalar2));
		}
		catch(ClassCastException e)
		{
			opstack.push(scalar1);
			opstack.push(scalar2);
			interpreter.handleError(e, this);
		}
	}
}
