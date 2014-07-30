package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _xor extends PsiOperator
{
	@Override
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject logical2=opstack.pop();
		PsiObject logical1=opstack.pop();
		try
		{
			opstack.push((PsiObject)((PsiLogical)logical1).psiXor((PsiLogical)logical2));
		}
		catch(ClassCastException e)
		{
			opstack.push(logical1);
			opstack.push(logical2);
			interpreter.error("typecheck", this);
		}
	}
}
