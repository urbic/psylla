package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _read extends PsiOperator
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

		PsiObject readable=opstack.pop();
		try
		{
			PsiInteger character=((PsiReadable)readable).psiRead();
			if(character.getValue()==-1)
				opstack.push(new PsiBoolean(false));
			else
			{
				opstack.push(character);
				opstack.push(new PsiBoolean(true));
			}
		}
		catch(ClassCastException e)
		{
			opstack.push(readable);
			interpreter.error("typecheck", this);
		}
		catch(PsiException e)
		{
			opstack.push(readable);
			interpreter.error(e.kind(), this);
		}
	}
}
