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
			throw new PsiException("ioerror");
		}
	}

	public PsiFileWriter(PsiString oName)
		throws PsiException
	{
		this(oName.getValue());
	}

}
