package coneforest.psi.core;
import coneforest.psi.*;

public class _filesize extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject stringlike=opstack.pop();
		try
		{
			String name=Utility.fileNameToNative(((PsiStringlike)stringlike).getString());
			try
			{
				java.nio.file.attribute.BasicFileAttributes attrs
					=java.nio.file.Files.readAttributes((new java.io.File(name)).toPath(), java.nio.file.attribute.BasicFileAttributes.class);
				opstack.push(new PsiInteger(attrs.size()));
			}
			catch(java.io.IOException e)
			{
				throw new PsiException("ioerror");
			}
			catch(SecurityException e)
			{
				throw new PsiException("security");
			}
		}
		catch(ClassCastException|PsiException e)
		{
			opstack.push(stringlike);
			interpreter.error(e, this);
		}
	}
}
