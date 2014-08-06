package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _toreal extends PsiOperator
{
	@Override
	public void invoke(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject convertable=opstack.pop();
		try
		{
			opstack.push(((PsiConvertableToReal)convertable).psiToReal());
		}
		catch(ClassCastException e)
		{
			opstack.push(convertable);
			interpreter.error("typecheck", this);
		}
		catch(PsiException e)
		{
			opstack.push(convertable);
			interpreter.error(e.kind(), this);
		}
	}
}
