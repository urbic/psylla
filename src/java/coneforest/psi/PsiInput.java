package coneforest.psi;

public class PsiInput
	implements
		PsiReadable,
		PsiCloseable
{
	public PsiInput()
	{
	}

	public PsiInput(java.io.InputStream input)
	{
		setInput(input);
	}

	@Override
	public String getTypeName()
	{
		return "input";
	}

	public void setInput(java.io.InputStream input)
	{
		this.input=input;
	}

	public java.io.InputStream getInput()
	{
		return input;
	}

	@Override
	public PsiInteger psiRead()
		throws PsiException
	{
		try
		{
			return PsiInteger.valueOf(input.read());
		}
		catch(java.io.IOException e)
		{
			throw new PsiException("ioerror");
		}
	}

	@Override
	public PsiString psiReadString(PsiInteger count)
		throws PsiException
	{
		throw new PsiException("TODO");
	}

	@Override
	public PsiString psiReadLine(PsiStringy eol)
		throws PsiException
	{
		throw new PsiException("TODO");
	}

	@Override
	public void psiClose()
		throws PsiException
	{
		try
		{
			input.close();
		}
		catch(java.io.IOException e)
		{
			throw new PsiException("ioerror");
		}
	}

	@Override
	public PsiBoolean psiReady()
		throws PsiException
	{
		try
		{
			return PsiBoolean.valueOf(input.available()>0);
		}
		catch(java.io.IOException e)
		{
			throw new PsiException("ioerror");
		}
	}

	@Override
	public void psiSkip(PsiInteger count)
		throws PsiException
	{
		long countValue=count.longValue();
		try
		{
			input.skip(countValue);
		}
		catch(IllegalArgumentException e)
		{
			throw new PsiException("rangecheck");
		}
		catch(java.io.IOException e)
		{
			throw new PsiException("ioerror");
		}
	}

	private java.io.InputStream input;
}
