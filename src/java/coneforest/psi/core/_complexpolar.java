package coneforest.psi.core;
import coneforest.psi.*;

public class _complexpolar extends PsiOperator
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

		final PsiObject numeric2=opstack.pop();
		final PsiObject numeric1=opstack.pop();
		try
		{
			opstack.push(PsiComplex.psiFromPolar((PsiNumeric)numeric1, (PsiNumeric)numeric2));
		}
		catch(ClassCastException e)
		{
			opstack.push(numeric1);
			opstack.push(numeric2);
			interpreter.error(e, this);
		}
	}
}
