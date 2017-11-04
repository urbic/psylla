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
		throws PsyException
	{
		try
		{
			output.write(b);
		}
		catch(final java.io.IOException e)
		{
			throw new PsyIOErrorException();
		}
	}

	public void write(final PsyInteger oCharacter)
		throws PsyException
	{
		write(oCharacter.intValue());
	}

	@Override
	public void psyFlush()
		throws PsyException
	{
		try
		{
			output.flush();
		}
		catch(final java.io.IOException e)
		{
			throw new PsyIOErrorException();
		}
	}

	@Override
	public void psyClose()
		throws PsyException
	{
		try
		{
			output.close();
		}
		catch(final java.io.IOException e)
		{
			throw new PsyIOErrorException();
		}
	}

	private java.io.OutputStream output;
}
