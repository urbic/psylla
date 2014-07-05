package coneforest.psi;

public class PsiFileReader extends PsiReader
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
			throw new PsiException("undefinedfilename");
		}
	}

	public PsiFileReader(PsiString oName)
		throws PsiException
	{
		this(oName.getValue());
	}

}
