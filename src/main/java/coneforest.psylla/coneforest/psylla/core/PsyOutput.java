package coneforest.psylla.core;

import coneforest.psylla.runtime.*;
import java.io.IOException;
import java.io.OutputStream;

/**
*	The representation of {@code output}.
*/
@Type("output")
abstract public class PsyOutput
	implements
		PsyCloseable,
		PsyFlushable,
		PsyWritable
{
	public PsyOutput(final OutputStream output)
	{
		setOutput(output);
	}

	public void setOutput(final OutputStream output)
	{
		this.output=output;
	}

	public OutputStream getOutput()
	{
		return output;
	}

	public void write(final int b)
		throws PsyIOErrorException
	{
		try
		{
			output.write(b);
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException();
		}
	}

	public void write(final PsyInteger oCharacter)
		throws PsyIOErrorException
	{
		write(oCharacter.intValue());
	}

	@Override
	public void psyFlush()
		throws PsyIOErrorException
	{
		try
		{
			output.flush();
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException();
		}
	}

	@Override
	public void psyClose()
		throws PsyIOErrorException
	{
		try
		{
			output.close();
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException();
		}
	}

	private OutputStream output;
}
