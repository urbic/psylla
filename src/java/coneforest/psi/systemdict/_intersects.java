package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _intersects extends PsiOperator
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

		PsiObject setlike2=opstack.pop();
		PsiObject setlike1=opstack.pop();
		try
		{
			opstack.push(((PsiSetlike)setlike1).psiIntersects((PsiSetlike)setlike2));
		}
		catch(ClassCastException e)
		{
			opstack.push(setlike1);
			opstack.push(setlike2);
			interpreter.error("typecheck", this);
		}
	}
}
