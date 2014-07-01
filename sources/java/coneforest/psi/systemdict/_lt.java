package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _lt extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.error("stackunderflow");
			return;
		}
		PsiObject obj2=opstack.pop();
		PsiObject obj1=opstack.pop();

		if(obj1 instanceof PsiNumeric && obj2 instanceof PsiNumeric)
			opstack.push(PsiNumeric.lt((PsiNumeric)obj1, (PsiNumeric)obj2));
		else if(obj1 instanceof PsiStringlike && obj2 instanceof PsiStringlike)
			opstack.push(PsiStringlike.lt((PsiStringlike)obj1, (PsiStringlike)obj2));
		else
			interpreter.error("typecheck");
	}
}
