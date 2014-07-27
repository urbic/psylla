package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _filereader extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();

		if(opstack.size()<1)
			interpreter.error("stackunderflow", this);

		PsiObject name=opstack.pop();
		try
		{
			opstack.push(new PsiFileReader((PsiString)name));
		}
		catch(ClassCastException e)
		{
			interpreter.error("typecheck", this);
		}
		catch(PsiException e)
		{
			interpreter.error(e.kind(), this);
		}
	}
}
