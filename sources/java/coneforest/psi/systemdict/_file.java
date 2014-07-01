package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _file extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();

		if(opstack.size()<2)
			interpreter.error("stackunderflow");

		PsiObject mode=opstack.pop();
		PsiObject name=opstack.pop();
		if(name instanceof PsiString && mode instanceof PsiString)
		{
			try
			{
				opstack.push(new PsiFile(((PsiString)name).getValue(), ((PsiString)mode).getValue()));
			}
			catch(java.io.FileNotFoundException e)
			{
				interpreter.error("undefinedfilename");
			}
		}
		else
			interpreter.error("typecheck");
	}
}
