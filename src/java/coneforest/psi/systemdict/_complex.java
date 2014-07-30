package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _complex extends PsiOperator
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

		PsiObject numeric2=opstack.pop();
		PsiObject numeric1=opstack.pop();
		try
		{
			opstack.push(new PsiComplex((PsiNumeric)numeric1, (PsiNumeric)numeric2));
		}
		catch(ClassCastException e)
		{
			opstack.push(numeric1);
			opstack.push(numeric2);
			interpreter.error("typecheck", this);
		}
	}
}
