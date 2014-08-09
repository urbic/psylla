package coneforest.psi.core;
import coneforest.psi.*;

public class _getinterval extends PsiOperator
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

		PsiObject count=opstack.pop();
		PsiObject index=opstack.pop();
		PsiObject arraylike=opstack.pop();
		try
		{
			opstack.push((PsiObject)((PsiArraylike)arraylike).psiGetInterval((PsiInteger)index, (PsiInteger)count));
		}
		catch(Exception e)
		{
			opstack.push(arraylike);
			opstack.push(index);
			opstack.push(count);
			interpreter.error(e, this);
		}
	}
}
