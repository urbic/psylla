package coneforest.psylla.core;

@coneforest.psylla.Type("input")
public class PsyInput
	implements
		PsyCloseable,
		PsyReadable,
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
		throws PsyException
	{
		try
		{
			return input.read();
		}
		catch(final java.io.IOException e)
		{
			throw new PsyIOErrorException();
		}
	}

	@Override
	public PsyString psyReadString(final PsyInteger oCount)
		throws PsyException
	{
		// TODO
		throw new PsyUnsupportedException();
	}

	@Override
	public PsyString psyReadLine()
		throws PsyException
	{
		// TODO
		throw new PsyUnsupportedException();
	}

	@Override
	public void psyClose()
		throws PsyException
	{
		try
		{
			input.close();
		}
		catch(final java.io.IOException e)
		{
			throw new PsyIOErrorException();
		}
	}

	@Override
	public PsyBoolean psyReady()
		throws PsyException
	{
		try
		{
			return PsyBoolean.valueOf(input.available()>0);
		}
		catch(final java.io.IOException e)
		{
			throw new PsyIOErrorException();
		}
	}

	@Override
	public PsyBoolean psySkip(final PsyInteger oCount)
		throws PsyException
	{
		final long count=oCount.longValue();
		try
		{
			return PsyBoolean.valueOf(count==input.skip(count));
		}
		catch(final IllegalArgumentException e)
		{
			throw new PsyRangeCheckException();
		}
		catch(final java.io.IOException e)
		{
			throw new PsyIOErrorException();
		}
	}

	@Override
	public void psyReset()
		throws PsyException
	{
		try
		{
			input.reset();
		}
		catch(final java.io.IOException e)
		{
			throw new PsyIOErrorException();
		}
	}

	private java.io.InputStream input;
}
