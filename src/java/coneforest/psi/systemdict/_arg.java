package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _arg extends PsiOperator
{
	@Override
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject cn=opstack.pop();
		try
		{
			opstack.push(((PsiComplexNumeric)cn).psiArg());
		}
		catch(ClassCastException e)
		{
			opstack.push(cn);
			interpreter.error("typecheck", this);
		}
	}
}
