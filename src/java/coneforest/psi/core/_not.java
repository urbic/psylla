package coneforest.psi.core;
import coneforest.psi.*;

public class _not extends PsiOperator
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

		PsiObject logical=opstack.pop();
		try
		{
			opstack.push((PsiObject)((PsiLogical)logical).psiNot());
		}
		catch(ClassCastException e)
		{
			opstack.push(logical);
			interpreter.error(e, this);
		}
	}
}
