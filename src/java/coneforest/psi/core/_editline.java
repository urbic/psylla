package coneforest.psi.core;
import coneforest.psi.*;

public class _editline extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		try
		{
			if(consoleReader==null)
				consoleReader=new jline.ConsoleReader();
			interpreter.getOperandStack().push(new PsiString(consoleReader.readLine()+"\n"));
		}
		catch(java.io.IOException e)
		{
			interpreter.handleError("ioerror", this);
		}
		catch(ClassCastException e)
		{
			interpreter.handleError(e, this);
		}
	}

	private jline.ConsoleReader consoleReader;
}
