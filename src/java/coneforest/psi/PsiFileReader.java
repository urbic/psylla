package coneforest.psi;

public class PsiFileReader
	extends PsiReader
{
	public PsiFileReader(String name)
		throws PsiException
	{
		try
		{
			setReader(new java.io.FileReader(java.io.File.separatorChar=='/'? name: name.replace('/', java.io.File.separatorChar)));
		}
		catch(java.io.FileNotFoundException e)
		{
			throw new PsiException("undefinedfilename");
		}
	}

	public PsiFileReader(PsiStringlike name)
		throws PsiException
	{
		this(name.getString());
	}
}
