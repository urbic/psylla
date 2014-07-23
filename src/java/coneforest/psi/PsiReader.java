package coneforest.psi;

public class PsiReader extends PsiObject implements PsiCloseable
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

	public java.io.Reader getReader()
	{
		return reader;
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

	public void close()
		throws PsiException
	{
		try
		{
			reader.close();
		}
		catch(java.io.IOException e)
		{
			throw new PsiException("ioerror");
		}
	}

	private java.io.Reader reader;
}
