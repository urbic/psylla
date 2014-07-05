package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _undef extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
			interpreter.error("stackunderflow");
		else
		{
			PsiObject key=opstack.pop();
			PsiObject dict=opstack.pop();

			if(key instanceof PsiStringlike && dict instanceof PsiDictionary)
				((PsiDictionary)dict).undef((PsiStringlike)key);
			else
				interpreter.error("typecheck");
			// TODO errors: invalidaccess
		}
	}
}
