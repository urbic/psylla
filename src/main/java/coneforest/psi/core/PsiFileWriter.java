package coneforest.psi.core;

public class PsiFileWriter
	extends PsiWriter
{
	public PsiFileWriter(final String fileName)
		throws PsiException
	{
		super(newFileWriter(fileName));
		/*
		try
		{
			setWriter(new java.io.FileWriter(
					coneforest.psi.FileSystem.getPath(fileName).toString()));
		}
		catch(final java.io.FileNotFoundException e)
		{
			throw new PsiFileNotFoundException();
		}
		catch(final java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
		*/
	}

	public PsiFileWriter(final PsiStringy oFileName)
		throws PsiException
	{
		this(oFileName.stringValue());
	}

	private static java.io.FileWriter newFileWriter(final String fileName)
		throws PsiException
	{
		try
		{
			return new java.io.FileWriter(
					coneforest.psi.FileSystem.getPath(fileName).toString());
		}
		catch(final java.io.FileNotFoundException e)
		{
			throw new PsiFileNotFoundException();
		}
		catch(final java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
	}
}
