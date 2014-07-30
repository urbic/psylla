package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _undef extends PsiOperator
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

		PsiObject key=opstack.pop();
		PsiObject dict=opstack.pop();
		try
		{
			((PsiDictionary)dict).psiUndef((PsiStringlike)key);
		}
		catch(ClassCastException e)
		{
			interpreter.error("typecheck", this);
			// TODO errors: invalidaccess
		}
	}
}
