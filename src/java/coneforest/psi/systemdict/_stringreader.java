package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _stringreader extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();

		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject string=opstack.pop();
		try
		{
			opstack.push(new PsiStringReader((PsiString)string));
		}
		catch(ClassCastException e)
		{
			opstack.push(string);
			interpreter.error("typecheck", this);
		}
	}
}
