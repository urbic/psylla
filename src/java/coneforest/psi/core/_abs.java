package coneforest.psi.core;
import coneforest.psi.*;

/**
 * The implementation of the <code>abs</code> operator.
 * 
 */
public class _abs extends PsiOperator
{
	@Override
	public void invoke(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject arithmetic=opstack.pop();
		try
		{
			opstack.push(((PsiArithmetic)arithmetic).psiAbs());
		}
		catch(ClassCastException e)
		{
			opstack.push(arithmetic);
			interpreter.error(e, this);
		}
	}
}
