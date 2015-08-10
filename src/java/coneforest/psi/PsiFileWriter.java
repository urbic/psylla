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
		catch(java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	public PsiFileWriter(PsiStringy name)
		throws PsiException
	{
		this(name.getString());
	}
}
