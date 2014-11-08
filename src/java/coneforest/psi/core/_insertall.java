package coneforest.psi.core;
import coneforest.psi.*;

public class _insertall extends PsiOperator
{
	@Override
	public void invoke(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<3)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject iterable=opstack.pop();
		PsiObject index=opstack.pop();
		PsiObject arraylike=opstack.pop();
		try
		{
			((PsiArraylike)arraylike).psiInsertAll((PsiInteger)index, (PsiIterable)iterable);
		}
		catch(ClassCastException|PsiException e)
		{
			opstack.push(arraylike);
			opstack.push(index);
			opstack.push(iterable);
			interpreter.error(e, this);
		}
	}
}
