package coneforest.psi.core;
import coneforest.psi.*;

public class _pow extends PsiOperator
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

		PsiObject cn2=opstack.pop();
		PsiObject cn1=opstack.pop();
		try
		{
			opstack.push(((PsiComplexNumeric)cn1).psiPow((PsiComplexNumeric)cn2));
		}
		catch(ClassCastException|PsiException e)
		{
			opstack.push(cn1);
			opstack.push(cn2);
			interpreter.error(e, this);
		}
	}
}
