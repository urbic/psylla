package coneforest.psylla.core;

@coneforest.psylla.Type("writer")
public class PsyWriter
	implements
		PsyCloseable,
		PsyFlushable,
		PsyWritable
{

	public PsyWriter(final java.io.Writer writer)
	{
		this.writer=writer;
	}

	public java.io.Writer writer()
	{
		return writer;
	}

	@Override
	public void psyWrite(final PsyInteger oCharacter)
		throws PsyException
	{
		try
		{
			writer.write(oCharacter.intValue());
		}
		catch(final java.io.IOException e)
		{
			throw new PsyIOErrorException();
		}
	}

	@Override
	public void psyWriteString(final PsyStringy oString)
		throws PsyException
	{
		try
		{
			writer.write(oString.stringValue());
		}
		catch(final java.io.IOException e)
		{
			throw new PsyIOErrorException();
		}
	}

	@Override
	public void psyFlush()
		throws PsyException
	{
		try
		{
			writer.flush();
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
			writer.close();
		}
		catch(final java.io.IOException e)
		{
			throw new PsyIOErrorException();
		}
	}

	private java.io.Writer writer;
}
