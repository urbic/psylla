package coneforest.psi;

public class PsiFileWriter extends PsiWriter
{
	public PsiFileWriter(String name)
		throws PsiException
	{
		try
		{
			setWriter(new java.io.FileWriter(name));
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

	public PsiFileWriter(PsiStringy oFileName)
		throws PsiException
	{
		this(oFileName.stringValue());
	}
}
