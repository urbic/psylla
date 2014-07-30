package coneforest.psi;

public class PsiWriter
	extends PsiObject
	implements PsiWritable, PsiCloseable, PsiFlushable
{
	public PsiWriter()
	{
	}

	public PsiWriter(java.io.Writer writer)
	{
		setWriter(writer);
	}

	@Override
	public String getTypeName()
	{
		return "writer";
	}

	public void setWriter(java.io.Writer writer)
	{
		this.writer=writer;
	}

	public java.io.Writer getWriter()
	{
		return writer;
	}

	@Override
	public void psiWrite(PsiInteger character)
		throws PsiException
	{
		try
		{
			writer.write(character.getValue().intValue());
		}
		catch(java.io.IOException e)
		{
			throw new PsiException("ioerror");
		}
	}

	@Override
	public void psiWriteString(PsiString string)
		throws PsiException
	{
		try
		{
			writer.write(string.getValue());
		}
		catch(java.io.IOException e)
		{
			throw new PsiException("ioerror");
		}
	}

	@Override
	public void psiFlush()
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

	@Override
	public void psiClose()
		throws PsiException
	{
		try
		{
			writer.close();
		}
		catch(java.io.IOException e)
		{
			throw new PsiException("ioerror");
		}
	}

	private java.io.Writer writer;
}
