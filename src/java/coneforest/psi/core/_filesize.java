package coneforest.psi.core;
import coneforest.psi.*;

public class _filesize extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		String name=Utility.fileNameToNative(((PsiStringlike)opstack.popOperands(1)[0]).getString());
		try
		{
			java.nio.file.attribute.BasicFileAttributes attrs
				=java.nio.file.Files.readAttributes((new java.io.File(name)).toPath(), java.nio.file.attribute.BasicFileAttributes.class);
			opstack.push(PsiInteger.valueOf(attrs.size()));
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
}
