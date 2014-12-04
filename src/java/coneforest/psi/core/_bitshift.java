package coneforest.psi.core;
import coneforest.psi.*;

public class _bitshift extends PsiOperator
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

		final PsiObject shift=opstack.pop();
		final PsiObject obj=opstack.pop();
		try
		{
			opstack.push(((PsiInteger)obj).psiBitShift((PsiInteger)shift));
		}
		catch(ClassCastException e)
		{
			opstack.push(obj);
			opstack.push(shift);
			interpreter.error(e, this);
		}
	}
}
