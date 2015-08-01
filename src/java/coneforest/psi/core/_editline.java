package coneforest.psi.core;
import coneforest.psi.*;

public class _editline extends PsiOperator
{
	public _editline()
		throws PsiException
	{
		super();
		try
		{
			consoleReader=new jline.ConsoleReader();
		}
		catch(java.io.IOException e)
		{
			throw new PsiException("ioerror");
		}
	}

	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		try
		{
			interpreter.getOperandStack().push(new PsiString(consoleReader.readLine()+"\n"));
		}
		catch(java.io.IOException e)
		{
			throw new PsiException("ioerror");
		}
	}

	private jline.ConsoleReader consoleReader;
}
