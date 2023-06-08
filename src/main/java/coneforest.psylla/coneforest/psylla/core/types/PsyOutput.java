package coneforest.psylla.core.types;

import coneforest.psylla.core.errors.PsyError;
import coneforest.psylla.core.errors.PsyIOError;
import java.io.IOException;
import java.io.OutputStream;

// TODO
@coneforest.psylla.Type("output")
abstract public class PsyOutput
	implements
		PsyCloseable,
		PsyFlushable,
		PsyWritable
{
	public PsyOutput(final java.io.OutputStream output)
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
		throws PsyIOError
	{
		try
		{
			output.write(b);
		}
		catch(final IOException ex)
		{
			throw new PsyIOError();
		}
	}

	public void write(final PsyInteger oCharacter)
		throws PsyError
	{
		write(oCharacter.intValue());
	}

	@Override
	public void psyFlush()
		throws PsyError
	{
		try
		{
			output.flush();
		}
		catch(final IOException ex)
		{
			throw new PsyIOError();
		}
	}

	@Override
	public void psyClose()
		throws PsyError
	{
		try
		{
			output.close();
		}
		catch(final IOException ex)
		{
			throw new PsyIOError();
		}
	}

	private OutputStream output;
}
