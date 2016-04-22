package coneforest.psi.core;

public class PsiFileReader
	extends PsiReader
{
	public PsiFileReader(String name)
		throws PsiException
	{
		try
		{
			setReader(new java.io.FileReader(name));
		}
		catch(java.io.FileNotFoundException e)
		{
			throw new PsiFileNotFoundException();
		}
	}

	public PsiFileReader(PsiStringy name)
		throws PsiException
	{
		this(name.stringValue());
	}
}
