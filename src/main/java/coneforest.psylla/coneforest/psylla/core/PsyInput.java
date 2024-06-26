package coneforest.psylla.core;

import coneforest.psylla.runtime.*;
import java.io.IOException;
import java.io.InputStream;

/**
*	The representation of {@code input}.
*/
@Type("input")
public class PsyInput
	implements
		PsyCloseable,
		PsyReadable,
		PsyReady,
		PsyResetable
{
	public PsyInput(final InputStream input)
	{
		setInput(input);
	}

	public void setInput(final InputStream input)
	{
		this.input=input;
	}

	public InputStream getInput()
	{
		return input;
	}

	@Override
	public int read()
		throws PsyIOErrorException
	{
		try
		{
			return input.read();
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException();
		}
	}

	@Override
	public PsyString psyReadString(final PsyInteger oCount)
		throws PsyUnsupportedException
	{
		// TODO
		throw new PsyUnsupportedException();
	}

	@Override
	public PsyString psyReadLine()
		throws PsyUnsupportedException
	{
		// TODO
		throw new PsyUnsupportedException();
	}

	@Override
	public void psyClose()
		throws PsyIOErrorException
	{
		try
		{
			input.close();
		}
		catch(final IOException ex)
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
		catch(final IOException ex)
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
		catch(final IOException ex)
		{
			throw new PsyIOErrorException();
		}
	}

	@Override
	public void psyReset()
		throws PsyIOErrorException
	{
		try
		{
			input.reset();
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException();
		}
	}

	private InputStream input;
}
