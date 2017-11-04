package coneforest.psylla.core;

import coneforest.psylla.*;
import java.io.IOException;
import java.nio.CharBuffer;

@coneforest.psylla.Type("reader")
public class PsyReader
	implements
		PsyCloseable,
		PsyEvaluable,
		PsyReadable,
		PsyResettable
{
	public PsyReader(final java.io.Reader reader)
	{
		this.reader=reader;
	}

	public PsyReader(final java.io.InputStream is)
	{
		this(new java.io.InputStreamReader(is));
	}

	@Override
	public void eval(final Interpreter interpreter)
		throws PsyException
	{
		interpreter.interpret(this);
	}

	public java.io.Reader reader()
	{
		return reader;
	}

	@Override
	public int read()
		throws PsyException
	{
		try
		{
			return reader.read();
		}
		catch(final IOException e)
		{
			throw new PsyIOErrorException();
		}
	}

	@Override
	public PsyString psyReadString(final PsyInteger oCount)
		throws PsyException
	{
		final long count=oCount.longValue();
		if(count<=0)
			throw new PsyRangeCheckException();
		if(count>Integer.MAX_VALUE)
			throw new PsyLimitCheckException();
		try
		{
			///*
			final CharBuffer buffer=CharBuffer.allocate((int)count);
			reader.read(buffer);
			buffer.flip();
			return new PsyString(buffer.toString());
			//*/
			/*
			final char[] buffer=new char[(int)count];
			reader.read(buffer);
			return new PsyString(new String(buffer));
			*/
		}
		catch(final OutOfMemoryError e)
		{
			throw new PsyLimitCheckException();
		}
		catch(final IOException e)
		{
			throw new PsyIOErrorException();
		}
	}

	@Override
	public PsyString psyReadLine()
		throws PsyException
	{
		final StringBuilder sb=new StringBuilder();

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
		catch(final IOException e)
		{
			throw new PsyIOErrorException();
		}
	}

	@Override
	public PsyBoolean psySkip(final PsyInteger oCount)
		throws PsyException
	{
		try
		{
			final long count=oCount.longValue();
			return PsyBoolean.valueOf(count==reader.skip(count));
		}
		catch(final IllegalArgumentException e)
		{
			throw new PsyRangeCheckException();
		}
		catch(final IOException e)
		{
			throw new PsyIOErrorException();
		}
	}

	@Override
	public PsyBoolean psyReady()
		throws PsyException
	{
		try
		{
			return PsyBoolean.valueOf(reader.ready());
		}
		catch(final IOException e)
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
			reader.close();
		}
		catch(final IOException e)
		{
			throw new PsyIOErrorException();
		}
	}

	@Override
	public void psyReset()
		throws PsyException
	{
		try
		{
			reader.reset();
		}
		catch(final IOException e)
		{
			throw new PsyIOErrorException();
		}
	}

	public static final String LINE_SEPARATOR=System.getProperty("line.separator");

	protected java.io.Reader reader;
}
