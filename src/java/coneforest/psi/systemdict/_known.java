package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _known extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.error("stackunderflow");
			return;
		}
		
		PsiObject key=opstack.pop();
		PsiObject dict=opstack.pop();

		try
		{
			opstack.push(((PsiHashlike)dict).known((PsiStringlike)key));
		}
		catch(ClassCastException e)
		{
			interpreter.error("typecheck");
		}
	}
}
