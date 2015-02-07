package coneforest.psi.core;
import coneforest.psi.*;

public class _tointeger extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		final OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.handleError("stackunderflow", this);
			return;
		}

		final PsiObject convertable=opstack.pop();
		try
		{
			opstack.push(((PsiConvertableToInteger)convertable).psiToInteger());
		}
		catch(ClassCastException|PsiException e)
		{
			opstack.push(convertable);
			interpreter.handleError(e, this);
		}

		/*
		try
		{
			// TODO
			opstack.push(((PsiInteger)numeric).psiToInteger());
			//opstack.push(new PsiInteger(convertable));
		}
		catch(ClassCastException e)
		{
			opstack.push(numeric);
			interpreter.handleError("typecheck", this);
		}
		catch(PsiException e)
		{
			opstack.push(numeric);
			interpreter.handleError(e.kind(), this);
		}
		*/

		/*
		if(obj instanceof PsiInteger)
		{
			opstack.push(obj);
		}
		else if(obj instanceof PsiReal)
		{
			double objValue=((PsiReal)obj).getValue();
			if(objValue>=Long.MIN_VALUE && objValue<=Long.MAX_VALUE)
				opstack.push(new PsiInteger(((PsiReal)obj).getValue().intValue()));
			else
				interpreter.handleError("rangecheck", this);
		}
		else if(obj instanceof PsiString)
		{
			try
			{
				opstack.push(new PsiInteger(Long.parseLong(((PsiString)obj).getString())));
			}
			catch(NumberFormatException e)
			{
				interpreter.handleError("syntaxerror", this);
			}
		}
		else
			interpreter.handleError("typecheck", this);
		*/

	}
}
