package coneforest.psylla.core.types;

import coneforest.psylla.core.errors.PsyIOError;
import java.io.IOException;

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
		throws PsyIOError
	{
		try
		{
			writer.write(oCharacter.intValue());
		}
		catch(final IOException ex)
		{
			throw new PsyIOError();
		}
	}

	@Override
	public void psyWriteString(final PsyTextual oString)
		throws PsyIOError
	{
		try
		{
			writer.write(oString.stringValue());
		}
		catch(final IOException ex)
		{
			throw new PsyIOError();
		}
	}

	@Override
	public void psyFlush()
		throws PsyIOError
	{
		try
		{
			writer.flush();
		}
		catch(final IOException ex)
		{
			throw new PsyIOError();
		}
	}

	@Override
	public void psyClose()
		throws PsyIOError
	{
		try
		{
			writer.close();
		}
		catch(final IOException ex)
		{
			throw new PsyIOError();
		}
	}

	final private java.io.Writer writer;
}
