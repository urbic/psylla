package coneforest.psi.core;
import coneforest.psi.*;

public class _le extends PsiOperator
{
	@Override
	public void invoke(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject scalar2=opstack.pop();
		PsiObject scalar1=opstack.pop();
		try
		{
			opstack.push(((PsiScalar)scalar1).psiLe((PsiScalar)scalar2));
		}
		catch(ClassCastException e)
		{
			interpreter.error("typecheck", this);
		}
	}
}