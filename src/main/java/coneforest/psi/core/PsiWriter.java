package coneforest.psi.core;

public class PsiWriter
	implements
		PsiCloseable,
		PsiFlushable,
		PsiWritable
{

	public PsiWriter(final java.io.Writer writer)
	{
		this.writer=writer;
	}

	@Override
	public String typeName()
	{
		return "writer";
	}

	public java.io.Writer writer()
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
		catch(final java.io.IOException e)
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
		catch(final java.io.IOException e)
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
		catch(final java.io.IOException e)
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
		catch(final java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	private java.io.Writer writer;
}
