package coneforest.psylla.core;

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

	public void setOutput(final java.io.OutputStream output)
	{
		this.output=output;
	}

	public java.io.OutputStream getOutput()
	{
		return output;
	}

	public void write(final int b)
		throws PsyErrorException
	{
		try
		{
			output.write(b);
		}
		catch(final java.io.IOException ex)
		{
			throw new PsyIOErrorException();
		}
	}

	public void write(final PsyInteger oCharacter)
		throws PsyErrorException
	{
		write(oCharacter.intValue());
	}

	@Override
	public void psyFlush()
		throws PsyErrorException
	{
		try
		{
			output.flush();
		}
		catch(final java.io.IOException ex)
		{
			throw new PsyIOErrorException();
		}
	}

	@Override
	public void psyClose()
		throws PsyErrorException
	{
		try
		{
			output.close();
		}
		catch(final java.io.IOException ex)
		{
			throw new PsyIOErrorException();
		}
	}

	private java.io.OutputStream output;
}
