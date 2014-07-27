package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _cvs extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow", this);
			return;
		}
		PsiObject obj=opstack.pop();
		opstack.push(new PsiString(obj.toString()));
		/*
		if(obj instanceof PsiBoolean)
			opstack.push(new PsiString(((PsiBoolean)obj).toString()));
		else if(obj instanceof PsiNumeric)
			opstack.push(new PsiString(((PsiNumeric)obj).toString()));
		else if(obj instanceof PsiStringlike)
			opstack.push(new PsiString(((PsiStringlike)obj).toString()));
		else
			interpreter.error("typecheck");
		*/
	}
}
