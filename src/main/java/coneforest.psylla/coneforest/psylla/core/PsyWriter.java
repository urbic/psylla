package coneforest.psylla.core;

import coneforest.psylla.runtime.*;
import java.io.IOException;
import java.io.Writer;

/**
*	The representation of {@code writer}.
*/
@Type("writer")
public class PsyWriter
	implements
		PsyCloseable,
		PsyFlushable,
		PsyWritable
{
	/**
	*	Constructs a new {@code writer} object wrapping the given writer.
	*
	*	@param writer the writer.
	*/
	public PsyWriter(final Writer writer)
	{
		this.writer=writer;
	}

	/**
	*	Returns the underlying writer.
	*
	*	@return the underlying writer.
	*/
	public Writer writer()
	{
		return writer;
	}

	@Override
	public void psyWrite(final PsyInteger oCharacter)
		throws PsyIOErrorException
	{
		try
		{
			writer.write(oCharacter.intValue());
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException();
		}
	}

	@Override
	public void psyWriteString(final PsyTextual oString)
		throws PsyIOErrorException
	{
		try
		{
			writer.write(oString.stringValue());
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException();
		}
	}

	@Override
	public void psyFlush()
		throws PsyIOErrorException
	{
		try
		{
			writer.flush();
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
			writer.close();
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException();
		}
	}

	final private Writer writer;
}
