package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _lt extends PsiOperator
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

		PsiObject scalar2=opstack.pop();
		PsiObject scalar1=opstack.pop();
		try
		{
			opstack.push(((PsiScalar)scalar1).psiLt((PsiScalar)scalar2));
		}
		catch(ClassCastException e)
		{
			opstack.push(scalar1);
			opstack.push(scalar2);
			interpreter.error("typecheck", this);
		}
	}
}
