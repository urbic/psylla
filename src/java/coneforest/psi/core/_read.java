package coneforest.psi.core;
import coneforest.psi.*;

public class _read extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		final OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		final PsiObject readable=opstack.pop();
		try
		{
			PsiInteger character=((PsiReadable)readable).psiRead();
			boolean notEOF=(character.intValue()!=-1);
			if(notEOF)
				opstack.push(character);
			opstack.push(new PsiBoolean(notEOF));
		}
		catch(ClassCastException|PsiException e)
		{
			opstack.push(readable);
			interpreter.error(e, this);
		}
	}
}
