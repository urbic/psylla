package coneforest.psi.core;
import coneforest.psi.*;

public class _split extends PsiOperator
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

		PsiObject stringlike=opstack.pop();
		PsiObject regexp=opstack.pop();
		try
		{
			opstack.push(((PsiRegExp)regexp).psiSplit((PsiStringlike)stringlike));
		}
		catch(ClassCastException e)
		{
			opstack.push(regexp);
			opstack.push(stringlike);
			interpreter.error(e, this);
		}
	}
}
