package coneforest.psylla.core;

import coneforest.psylla.runtime.*;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.CharBuffer;
import java.util.Optional;

/**
*	The representation of {@code reader}.
*/
@Type("reader")
public class PsyReader
	implements
		PsyCloseable,
		PsyEvaluable,
		PsyReadable,
		PsyResetable
{
	private final Reader reader;

	/**
	*	Constructs a new {@code reader} object from the reader.
	*
	*	@param reader the reader.
	*/
	public PsyReader(final Reader reader)
	{
		this.reader=reader;
	}

	/**
	*	Constructs a new {@code reader} object from the input stream.
	*
	*	@param is the input stream.
	*/
	public PsyReader(final InputStream is)
	{
		this(new InputStreamReader(is));
	}

	@Override
	public void psyEval(final PsyContext oContext)
		throws PsyErrorException
	{
		oContext.interpret(this);
	}

	/**
	*	{@return the {@link Reader} object backed by this object}
	*/
	public Reader reader()
	{
		return reader;
	}

	@Override
	public int read()
		throws IOException, IOError
	{
		return reader.read();
	}

	@Override
	public Optional<PsyString> psyReadString(final PsyInteger oCount)
		throws
			PsyIOErrorException,
			PsyLimitCheckException,
			PsyRangeCheckException
	{
		final var count=oCount.longValue();
		if(count<=0)
			throw new PsyRangeCheckException();
		if(count>Integer.MAX_VALUE)
			throw new PsyLimitCheckException();
		try
		{
			final var buffer=CharBuffer.allocate((int)count);
			var r=reader.read(buffer);
			if(r==-1)
				return Optional.<PsyString>empty();
			buffer.flip();
			return Optional.<PsyString>of(new PsyString(buffer.toString()));
		}
		catch(final OutOfMemoryError ex)
		{
			throw new PsyLimitCheckException();
		}
		catch(final IOException|IOError ex)
		{
			throw new PsyIOErrorException();
		}
	}

	@Override
	public PsyInteger psySkip(final PsyInteger oCount)
		throws
			PsyIOErrorException,
			PsyRangeCheckException
	{
		try
		{
			final long count=oCount.longValue();
			//return PsyBoolean.of(count==reader.skip(count));
			return PsyInteger.of(reader.skip(count));
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
	public PsyBoolean psyReady()
		throws PsyIOErrorException
	{
		try
		{
			return PsyBoolean.of(reader.ready());
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
			reader.close();
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
			reader.reset();
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException();
		}
	}
}
