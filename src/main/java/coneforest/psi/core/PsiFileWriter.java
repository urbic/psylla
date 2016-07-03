package coneforest.psi.core;

public class PsiFileWriter
	extends PsiWriter
{
	public PsiFileWriter(final String fileName)
		throws PsiException
	{
		try
		{
			setWriter(new java.io.FileWriter(fileName));
		}
		catch(java.io.FileNotFoundException e)
		{
			throw new PsiFileNotFoundException();
		}
		catch(java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	public PsiFileWriter(final PsiStringy oFileName)
		throws PsiException
	{
		this(oFileName.stringValue());
	}
}
