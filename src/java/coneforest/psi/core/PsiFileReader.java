package coneforest.psi.core;

public class PsiFileReader
	extends PsiReader
{
	public PsiFileReader(final String fileName)
		throws PsiException
	{
		try
		{
			setReader(new java.io.FileReader(fileName));
		}
		catch(java.io.FileNotFoundException e)
		{
			throw new PsiFileNotFoundException();
		}
	}

	public PsiFileReader(final PsiStringy oFileName)
		throws PsiException
	{
		this(oFileName.stringValue());
	}
}
