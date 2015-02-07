package coneforest.psi.core;
import coneforest.psi.*;

/**
 * The implementation of the <code>abs</code> operator.
 * 
 */
public class _abs extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		final OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.handleError("stackunderflow", this);
			return;
		}

		final PsiObject arithmetic=opstack.pop();
		try
		{
			opstack.push(((PsiArithmetic)arithmetic).psiAbs());
		}
		catch(ClassCastException e)
		{
			opstack.push(arithmetic);
			interpreter.handleError(e, this);
		}
	}
}
