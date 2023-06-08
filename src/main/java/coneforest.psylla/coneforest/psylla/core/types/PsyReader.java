package coneforest.psylla.core.types;

import coneforest.psylla.Type;
import coneforest.psylla.core.errors.PsyError;
import coneforest.psylla.core.errors.PsyIOError;
import coneforest.psylla.core.errors.PsyLimitCheck;
import coneforest.psylla.core.errors.PsyRangeCheck;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.CharBuffer;

@Type("reader")
public class PsyReader
	implements
		PsyCloseable,
		PsyEvaluable,
		PsyReadable,
		PsyResetable
{
	public PsyReader(final Reader reader)
	{
		this.reader=reader;
	}

	public PsyReader(final InputStream is)
	{
		this(new InputStreamReader(is));
	}

	@Override
	public void psyEval()
		throws PsyError
	{
		PsyContext.psyCurrentContext().interpret(this);
	}

	public Reader reader()
	{
		return reader;
	}

	@Override
	public int read()
		throws PsyIOError
	{
		try
		{
			return reader.read();
		}
		catch(final IOException ex)
		{
			throw new PsyIOError();
		}
	}

	@Override
	public PsyString psyReadString(final PsyInteger oCount)
		throws
			PsyIOError,
			PsyLimitCheck,
			PsyRangeCheck
	{
		final long count=oCount.longValue();
		if(count<=0)
			throw new PsyRangeCheck();
		if(count>Integer.MAX_VALUE)
			throw new PsyLimitCheck();
		try
		{
			final var buffer=CharBuffer.allocate((int)count);
			reader.read(buffer);
			buffer.flip();
			return new PsyString(buffer.toString());
		}
		catch(final OutOfMemoryError ex)
		{
			throw new PsyLimitCheck();
		}
		catch(final IOException ex)
		{
			throw new PsyIOError();
		}
	}

	@Override
	public PsyString psyReadLine()
		throws PsyIOError
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
			throw new PsyIOError();
		}
	}

	@Override
	public PsyInteger psySkip(final PsyInteger oCount)
		throws
			PsyIOError,
			PsyRangeCheck
	{
		try
		{
			final long count=oCount.longValue();
			//return PsyBoolean.of(count==reader.skip(count));
			return PsyInteger.of(reader.skip(count));
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
	public PsyBoolean psyReady()
		throws PsyIOError
	{
		try
		{
			return PsyBoolean.of(reader.ready());
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
			reader.close();
		}
		catch(final IOException ex)
		{
			throw new PsyIOError();
		}
	}

	@Override
	public void psyReset()
		throws PsyIOError
	{
		try
		{
			reader.reset();
		}
		catch(final IOException ex)
		{
			throw new PsyIOError();
		}
	}

	public static final String LINE_SEPARATOR=System.getProperty("line.separator");

	final private Reader reader;
}
