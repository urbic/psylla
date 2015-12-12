package coneforest.psi;

public class PsiWriter
	implements
		PsiCloseable,
		PsiFlushable,
		PsiWritable
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
			writer.write(character.intValue());
		}
		catch(java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	@Override
	public void psiWriteString(PsiStringy string)
		throws PsiException
	{
		try
		{
			writer.write(string.stringValue());
		}
		catch(java.io.IOException e)
		{
			throw new PsiIOErrorException();
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
			throw new PsiIOErrorException();
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
			throw new PsiIOErrorException();
		}
	}

	private java.io.Writer writer;
}
