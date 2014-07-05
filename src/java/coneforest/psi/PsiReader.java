package coneforest.psi;

public class PsiReader extends PsiObject
{
	public PsiReader()
	{
	}

	public PsiReader(java.io.Reader reader)
	{
		setReader(reader);
	}

	public String getTypeName()
	{
		return "reader";
	}

	public String toString()
	{
		return "-reader-";
	}

	public void setReader(java.io.Reader reader)
	{
		this.reader=reader;
	}

	public int read()
		throws PsiException
	{
		try
		{
			return reader.read();
		}
		catch(java.io.IOException e)
		{
			throw new PsiException("ioerror");
		}
	}

	private java.io.Reader reader;
}
