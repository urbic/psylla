package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _length extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
			interpreter.error("stackunderflow");
		else
		{
			PsiObject obj=opstack.pop();

			if(obj instanceof PsiComposite)
				opstack.push(new PsiInteger(((PsiComposite)obj).length()));
			else
			{
				opstack.push(obj);
				interpreter.error("typecheck");
			}
		}
	}
}
