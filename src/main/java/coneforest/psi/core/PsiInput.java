package coneforest.psi.core;

public class PsiInput
	implements
		PsiCloseable,
		PsiReadable,
		PsiResettable
{
	public PsiInput(final java.io.InputStream input)
	{
		setInput(input);
	}

	@Override
	public String typeName()
	{
		return "input";
	}

	public void setInput(final java.io.InputStream input)
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
		catch(final java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	@Override
	public PsiString psiReadString(final PsiInteger oCount)
		throws PsiException
	{
		// TODO
		throw new PsiUnsupportedException();
	}

	@Override
	public PsiString psiReadLine()
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
		catch(final java.io.IOException e)
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
		catch(final java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	@Override
	public PsiBoolean psiSkip(final PsiInteger oCount)
		throws PsiException
	{
		final long count=oCount.longValue();
		try
		{
			return PsiBoolean.valueOf(count==input.skip(count));
		}
		catch(final IllegalArgumentException e)
		{
			throw new PsiRangeCheckException();
		}
		catch(final java.io.IOException e)
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
		catch(final java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	private java.io.InputStream input;
}
