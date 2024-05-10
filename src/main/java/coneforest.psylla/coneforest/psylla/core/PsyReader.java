package coneforest.psylla.core;

import coneforest.psylla.runtime.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.CharBuffer;

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

	public Reader reader()
	{
		return reader;
	}

	@Override
	public int read()
		throws PsyIOErrorException
	{
		try
		{
			return reader.read();
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException();
		}
	}

	@Override
	public PsyString psyReadString(final PsyInteger oCount)
		throws
			PsyIOErrorException,
			PsyLimitCheckException,
			PsyRangeCheckException
	{
		final long count=oCount.longValue();
		if(count<=0)
			throw new PsyRangeCheckException();
		if(count>Integer.MAX_VALUE)
			throw new PsyLimitCheckException();
		try
		{
			final var buffer=CharBuffer.allocate((int)count);
			reader.read(buffer);
			buffer.flip();
			return new PsyString(buffer.toString());
		}
		catch(final OutOfMemoryError ex)
		{
			throw new PsyLimitCheckException();
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException();
		}
	}

	@Override
	public PsyString psyReadLine()
		throws PsyIOErrorException
	{
		final var sb=new StringBuilder();

		try
		{
			do
			{
				int c=reader.read();
				if(c==-1)
					return new PsyString(sb);
				sb.append((char)c);
				if(sb.substring(sb.length()-LINE_SEPARATOR.length()).equals(LINE_SEPARATOR))
					return new PsyString(sb);
			}
			while(true);
		}
		catch(final IOException ex)
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

	/**
	*	Line separator string.
	*/
	public static final String LINE_SEPARATOR=System.getProperty("line.separator").intern();

	final private Reader reader;
}
