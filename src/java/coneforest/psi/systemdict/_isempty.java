package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _isempty extends PsiOperator
{
	@Override
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject composite=opstack.pop();
		try
		{
			opstack.push(((PsiComposite)composite).psiIsEmpty());
		}
		catch(ClassCastException e)
		{
			opstack.push(composite);
			interpreter.error("typecheck", this);
		}
	}
}
