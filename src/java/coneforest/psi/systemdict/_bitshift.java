package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _bitshift extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.error("stackunderflow", this);
			return;
		}
			
		PsiObject shift=opstack.pop();
		PsiObject obj=opstack.pop();
		try
		{
			opstack.push(PsiInteger.bitshift((PsiInteger)obj, (PsiInteger)shift));
		}
		catch(ClassCastException e)
		{
			opstack.push(obj);
			opstack.push(shift);
			interpreter.error("typecheck", this);
		}
	}
}
