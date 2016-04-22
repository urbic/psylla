package coneforest.psi.core;

public class PsiInput
	implements
		PsiCloseable,
		PsiReadable,
		PsiResettable
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
	public int read()
		throws PsiException
	{
		try
		{
			return input.read();
		}
		catch(java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	@Override
	public PsiString psiReadString(PsiInteger count)
		throws PsiException
	{
		// TODO
		throw new PsiUnsupportedException();
	}

	@Override
	public PsiString psiReadLine(PsiStringy eol)
		throws PsiException
	{
		// TODO
		throw new PsiUnsupportedException();
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
			throw new PsiIOErrorException();
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
			throw new PsiIOErrorException();
		}
	}

	@Override
	public PsiBoolean psiSkip(PsiInteger count)
		throws PsiException
	{
		long countValue=count.longValue();
		try
		{
			return PsiBoolean.valueOf(countValue==input.skip(countValue));
		}
		catch(IllegalArgumentException e)
		{
			throw new PsiRangeCheckException();
		}
		catch(java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	@Override
	public void psiReset()
		throws PsiException
	{
		try
		{
			input.reset();
		}
		catch(java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	private java.io.InputStream input;
}
