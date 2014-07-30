package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _idiv extends PsiOperator
{
	@Override
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject integer2=opstack.pop();
		PsiObject integer1=opstack.pop();
		try
		{
			opstack.push(((PsiInteger)integer1).psiIdiv((PsiInteger)integer2));
		}
		catch(ClassCastException e)
		{
			opstack.push(integer1);
			opstack.push(integer2);
			interpreter.error("typecheck", this);
		}
		catch(PsiException e)
		{
			opstack.push(integer1);
			opstack.push(integer2);
			interpreter.error(e.kind(), this);
		}
	}
}
