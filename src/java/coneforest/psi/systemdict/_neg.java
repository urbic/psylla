package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _neg extends PsiOperator
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

		PsiObject arihmetic=opstack.pop();
		try
		{
			opstack.push((PsiObject)((PsiArithmetic)arihmetic).psiNeg());
		}
		catch(ClassCastException e)
		{
			opstack.push(arihmetic);
			interpreter.error("typecheck", this);
		}
	}
}
