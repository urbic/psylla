package coneforest.psi.core;
import coneforest.psi.*;

public class _editline extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		try
		{
			if(consoleReader==null)
				consoleReader=new jline.ConsoleReader();
			interpreter.getOperandStack().push(new PsiString(consoleReader.readLine()+"\n"));
		}
		catch(java.io.IOException e)
		{
			throw new PsiException("ioerror");
		}
	}

	private jline.ConsoleReader consoleReader;
}
