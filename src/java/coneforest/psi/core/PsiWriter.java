package coneforest.psi.core;

public class PsiWriter
	implements
		PsiCloseable,
		PsiFlushable,
		PsiWritable
{
	public PsiWriter()
	{
	}

	public PsiWriter(final java.io.Writer writer)
	{
		setWriter(writer);
	}

	@Override
	public String getTypeName()
	{
		return "writer";
	}

	public void setWriter(final java.io.Writer writer)
	{
		this.writer=writer;
	}

	public java.io.Writer getWriter()
	{
		return writer;
	}

	@Override
	public void psiWrite(final PsiInteger oCharacter)
		throws PsiException
	{
		try
		{
			writer.write(oCharacter.intValue());
		}
		catch(java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	@Override
	public void psiWriteString(final PsiStringy oString)
		throws PsiException
	{
		try
		{
			writer.write(oString.stringValue());
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
