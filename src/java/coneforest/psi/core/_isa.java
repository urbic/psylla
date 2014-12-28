package coneforest.psi.core;
import coneforest.psi.*;

public class _isa extends PsiOperator
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

		final PsiObject stringlike=opstack.pop();
		final PsiObject obj=opstack.pop();
		try
		{
			opstack.push(obj.psiIsA((PsiStringlike)stringlike));
		}
		catch(ClassCastException e)
		{
			opstack.push(obj);
			opstack.push(stringlike);
			interpreter.error(e, this);
		}
	}
}
