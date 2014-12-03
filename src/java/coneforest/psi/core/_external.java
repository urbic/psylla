package coneforest.psi.core;
import coneforest.psi.*;

public class _external extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject stringlike=opstack.pop();
		try
		{
			opstack.push(((PsiClassLoader)interpreter.getSystemDictionary().psiGet("classpath"))
					.psiExternal(((PsiStringlike)stringlike).getString()));
		}
		catch(ClassCastException|PsiException e)
		{
			opstack.push(stringlike);
			interpreter.error(e, this);
		}
	}
}
