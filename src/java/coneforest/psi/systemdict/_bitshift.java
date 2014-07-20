package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _bitshift extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
			interpreter.error("stackunderflow");
		else
		{
			PsiObject shift=opstack.pop();
			PsiObject obj=opstack.pop();
			if(obj instanceof PsiInteger && shift instanceof PsiInteger)
				opstack.push(PsiInteger.bitshift((PsiInteger)obj, (PsiInteger)shift));
			else
			{
				opstack.push(obj);
				opstack.push(shift);
				interpreter.error("typecheck");
			}
		}
	}
}
