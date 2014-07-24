package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _length extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow");
			return;
		}

		PsiObject composite=opstack.pop();

		if(composite instanceof PsiComposite)
			opstack.push(new PsiInteger(((PsiComposite)composite).length()));
		else
		{
			opstack.push(composite);
			interpreter.error("typecheck");
		}
	}
}
