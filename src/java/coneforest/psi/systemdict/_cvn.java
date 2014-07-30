package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _cvn extends PsiOperator
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

		PsiObject stringlike=opstack.pop();
		try
		{
			opstack.push(new PsiName(((PsiStringlike)stringlike).getString()));
		}
		catch(ClassCastException e)
		{
			interpreter.error("typecheck", this);
		}
	}
}
