package coneforest.psylla.core;

@coneforest.psylla.Type("input")
public class PsyInput
	implements
		PsyCloseable,
		PsyReadable,
		PsyReady,
		PsyResetable
{
	public PsyInput(final java.io.InputStream input)
	{
		setInput(input);
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
		throws PsyErrorException
	{
		try
		{
			return input.read();
		}
		catch(final java.io.IOException ex)
		{
			throw new PsyIOErrorException();
		}
	}

	@Override
	public PsyString psyReadString(final PsyInteger oCount)
		throws PsyErrorException
	{
		// TODO
		throw new PsyUnsupportedException();
	}

	@Override
	public PsyString psyReadLine()
		throws PsyErrorException
	{
		// TODO
		throw new PsyUnsupportedException();
	}

	@Override
	public void psyClose()
		throws PsyErrorException
	{
		try
		{
			input.close();
		}
		catch(final java.io.IOException ex)
		{
			throw new PsyIOErrorException();
		}
	}

	@Override
	public PsyBoolean psyReady()
		throws PsyIOErrorException
	{
		try
		{
			return PsyBoolean.of(input.available()>0);
		}
		catch(final java.io.IOException ex)
		{
			throw new PsyIOErrorException();
		}
	}

	@Override
	public PsyInteger psySkip(final PsyInteger oCount)
		throws PsyRangeCheckException, PsyIOErrorException
	{
		try
		{
			return PsyInteger.of(input.skip(oCount.longValue()));
		}
		catch(final IllegalArgumentException ex)
		{
			throw new PsyRangeCheckException();
		}
		catch(final java.io.IOException ex)
		{
			throw new PsyIOErrorException();
		}
	}

	@Override
	public void psyReset()
		throws PsyErrorException
	{
		try
		{
			input.reset();
		}
		catch(final java.io.IOException ex)
		{
			throw new PsyIOErrorException();
		}
	}

	private java.io.InputStream input;
}
