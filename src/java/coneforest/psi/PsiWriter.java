package coneforest.psi;

public class PsiWriter extends PsiObject
{
	public PsiWriter()
	{
	}

	public PsiWriter(java.io.Writer writer)
	{
		setWriter(writer);
	}

	public String getTypeName()
	{
		return "writer";
	}

	public String toString()
	{
		return "-writer-";
	}

	public void setWriter(java.io.Writer writer)
	{
		this.writer=writer;
	}

	public java.io.Writer getWriter()
	{
		return writer;
	}

	public void write(String string)
		throws PsiException
	{
		try
		{
			writer.write(string);
		}
		catch(java.io.IOException e)
		{
			throw new PsiException("ioerror");
		}
	}

	public void write(PsiString oString)
		throws PsiException
	{
		try
		{
			writer.write(oString.getValue());
		}
		catch(java.io.IOException e)
		{
			throw new PsiException("ioerror");
		}
	}

	public void flush()
		throws PsiException
	{
		try
		{
			writer.flush();
		}
		catch(java.io.IOException e)
		{
			throw new PsiException("ioerror");
		}
	}

	private java.io.Writer writer;
}
