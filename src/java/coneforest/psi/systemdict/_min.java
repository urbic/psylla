package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _min extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject scalar2=opstack.pop();
		PsiObject scalar1=opstack.pop();
		try
		{
			opstack.push(((PsiScalar)scalar1).lt((PsiScalar)scalar2).getValue()? scalar1: scalar2);
		}
		catch(ClassCastException e)
		{
			opstack.push(scalar1);
			opstack.push(scalar2);
			interpreter.error("typecheck", this);
		}
	}
}
