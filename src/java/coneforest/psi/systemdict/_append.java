package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _append extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.error("stackunderflow");
			return;
		}
		PsiObject obj=opstack.pop();
		PsiObject set=opstack.pop();

		if(set instanceof PsiBitSet && obj instanceof PsiInteger)
		{
			try
			{
				((PsiBitSet)set).append((PsiInteger)obj);
			}
			catch(PsiException e)
			{
				interpreter.error(e.kind());
			}
		}
		else
		{
			opstack.push(set);
			opstack.push(obj);
			interpreter.error("typecheck");
		}
	}
}
