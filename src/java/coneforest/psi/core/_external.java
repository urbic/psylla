package coneforest.psi.core;
import coneforest.psi.*;

public class _external extends PsiOperator
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

		final PsiObject stringlike=opstack.pop();
		try
		{
			opstack.push(((PsiClassLoader)interpreter.getSystemDictionary().psiGet("classpath"))
					.psiExternal(((PsiStringlike)stringlike).getString()));
		}
		catch(ClassCastException|PsiException e)
		{
			opstack.push(stringlike);
			interpreter.handleError(e, this);
		}
	}
}
