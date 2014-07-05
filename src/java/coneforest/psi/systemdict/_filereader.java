package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _filereader extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();

		if(opstack.size()<1)
			interpreter.error("stackunderflow");

		PsiObject name=opstack.pop();
		if(name instanceof PsiString)
		{
			try
			{
				opstack.push(new PsiFileReader((PsiString)name));
			}
			catch(PsiException e)
			{
				interpreter.error(e.kind());
			}
		}
		else
			interpreter.error("typecheck");
	}
}
