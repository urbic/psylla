package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _uniformdeviate extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject numeric=opstack.pop();
		PsiObject random=opstack.pop();
		try
		{
			opstack.push(((PsiRandom)random).psiUniformDeviate((PsiNumeric)numeric));
		}
		catch(ClassCastException e)
		{
			opstack.push(random);
			opstack.push(numeric);
			interpreter.error("typecheck", this);
		}
		catch(PsiException e)
		{
			opstack.push(random);
			opstack.push(numeric);
			interpreter.error(e.kind(), this);
		}
	}
}
