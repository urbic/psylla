package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _tointeger extends PsiOperator
{
	@Override
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject obj=opstack.pop();

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
			interpreter.error("typecheck", this);
		}
		catch(PsiException e)
		{
			opstack.push(numeric);
			interpreter.error(e.kind(), this);
		}
		*/
		
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
				interpreter.error("rangecheck", this);
		}
		else if(obj instanceof PsiString)
		{
			try
			{
				opstack.push(new PsiInteger(Long.parseLong(((PsiString)obj).getString())));
			}
			catch(NumberFormatException e)
			{
				interpreter.error("syntaxerror", this);
			}
		}
		else
			interpreter.error("typecheck", this);
		
	}
}
