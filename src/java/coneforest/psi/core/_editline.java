package coneforest.psi.core;
import coneforest.psi.*;

public class _editline extends PsiOperator
{
	@Override
	public void invoke(Interpreter interpreter)
	{
		try
		{
			if(consoleReader==null)
				consoleReader=new jline.ConsoleReader();
			interpreter.getOperandStack().push(new PsiString(consoleReader.readLine()+"\n"));
		}
		catch(java.io.IOException e)
		{
			interpreter.error("ioerror", this);
		}
		catch(Exception e)
		{
			interpreter.error(e, this);
		}
	}

	private jline.ConsoleReader consoleReader;
}
