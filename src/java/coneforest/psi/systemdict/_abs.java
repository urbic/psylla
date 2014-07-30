package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _abs extends PsiOperator
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

		PsiObject arithmetic=opstack.pop();
		try
		{
			opstack.push(((PsiArithmetic)arithmetic).psiAbs());
		}
		catch(ClassCastException e)
		{
			interpreter.error("typecheck", this);
		}
	}
}
