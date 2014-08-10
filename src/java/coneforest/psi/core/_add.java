package coneforest.psi.core;
import coneforest.psi.*;

/**
 * The implementation of the <code>add</code> operator
 */
public class _add extends PsiOperator
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

		PsiObject arithmetic2=opstack.pop();
		PsiObject arithmetic1=opstack.pop();
		try
		{
			opstack.push((PsiObject)((PsiArithmetic)arithmetic1).psiAdd((PsiArithmetic)arithmetic2));
		}
		catch(Exception e)
		{
			opstack.push(arithmetic1);
			opstack.push(arithmetic2);
			interpreter.error(e, this);
		}
	}
}
