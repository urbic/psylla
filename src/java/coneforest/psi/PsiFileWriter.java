package coneforest.psi;

public class PsiFileWriter extends PsiWriter
{
	public PsiFileWriter(String name)
		throws PsiException
	{
		try
		{
			setWriter(new java.io.FileWriter(java.io.File.separatorChar=='/'? name: name.replace('/', java.io.File.separatorChar)));
		}
		catch(java.io.IOException e)
		{
			throw new PsiException("ioerror");
		}
	}

	public PsiFileWriter(PsiStringlike name)
		throws PsiException
	{
		this(name.getString());
	}

}
