package coneforest.psylla.core.types;

import coneforest.psylla.core.errors.PsyError;
import coneforest.psylla.core.errors.PsyIOError;
import coneforest.psylla.core.errors.PsyRangeCheck;
import coneforest.psylla.core.errors.PsyUnsupported;
import java.io.IOException;
import java.io.InputStream;

@coneforest.psylla.Type("input")
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
		throws PsyError
	{
		try
		{
			return input.read();
		}
		catch(final IOException ex)
		{
			throw new PsyIOError();
		}
	}

	@Override
	public PsyString psyReadString(final PsyInteger oCount)
		throws PsyError
	{
		// TODO
		throw new PsyUnsupported();
	}

	@Override
	public PsyString psyReadLine()
		throws PsyError
	{
		// TODO
		throw new PsyUnsupported();
	}

	@Override
	public void psyClose()
		throws PsyError
	{
		try
		{
			input.close();
		}
		catch(final IOException ex)
		{
			throw new PsyIOError();
		}
	}

	@Override
	public PsyBoolean psyReady()
		throws PsyIOError
	{
		try
		{
			return PsyBoolean.of(input.available()>0);
		}
		catch(final IOException ex)
		{
			throw new PsyIOError();
		}
	}

	@Override
	public PsyInteger psySkip(final PsyInteger oCount)
		throws PsyRangeCheck, PsyIOError
	{
		try
		{
			return PsyInteger.of(input.skip(oCount.longValue()));
		}
		catch(final IllegalArgumentException ex)
		{
			throw new PsyRangeCheck();
		}
		catch(final IOException ex)
		{
			throw new PsyIOError();
		}
	}

	@Override
	public void psyReset()
		throws PsyError
	{
		try
		{
			input.reset();
		}
		catch(final IOException ex)
		{
			throw new PsyIOError();
		}
	}

	private InputStream input;
}
