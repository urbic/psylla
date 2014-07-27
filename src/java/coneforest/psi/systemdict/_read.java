package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _read extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject file=opstack.pop();
		try
		{
			int character=((PsiReader)file).read();
			if(character==-1)
				opstack.push(new PsiBoolean(false));
			else
			{
				opstack.push(new PsiInteger(character));
				opstack.push(new PsiBoolean(true));
			}
		}
		catch(ClassCastException e)
		{
			opstack.push(file);
			interpreter.error("typecheck", this);
		}
		catch(PsiException e)
		{
			opstack.push(file);
			interpreter.error(e.kind(), this);
		}
	}
}
