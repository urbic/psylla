package coneforest.psi.core;
import coneforest.psi.*;

@coneforest.psi.Type("reader")
public class PsiReader
	implements
		PsiCloseable,
		PsiEvaluable,
		PsiReadable,
		PsiResettable
{
	public PsiReader(final java.io.Reader reader)
	{
		this.reader=reader;
	}

	public PsiReader(final java.io.InputStream is)
	{
		this(new java.io.InputStreamReader(is));
	}

	@Override
	public void eval(final Interpreter interpreter)
		throws PsiException
	{
		interpreter.interpret(this);
	}

	public java.io.Reader reader()
	{
		return reader;
	}

	@Override
	public int read()
		throws PsiException
	{
		try
		{
			return reader.read();
		}
		catch(final java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	@Override
	public PsiString psiReadString(final PsiInteger oCount)
		throws PsiException
	{
		final long count=oCount.longValue();
		if(count<=0)
			throw new PsiRangeCheckException();
		if(count>Integer.MAX_VALUE)
			throw new PsiLimitCheckException();
		try
		{
			///*
			final java.nio.CharBuffer buffer=java.nio.CharBuffer.allocate((int)count);
			reader.read(buffer);
			buffer.flip();
			return new PsiString(buffer.toString());
			//*/
			/*final char[] buffer=new char[(int)count];
			reader.read(buffer);
			return new PsiString(new String(buffer));
			*/
		}
		catch(final OutOfMemoryError e)
		{
			throw new PsiLimitCheckException();
		}
		catch(final java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	@Override
	public PsiString psiReadLine()
		throws PsiException
	{
		final StringBuilder sb=new StringBuilder();

		try
		{
			do
			{
				int c=reader.read();
				if(c==-1)
					return new PsiString(sb);
				sb.append((char)c);
				if(sb.substring(sb.length()-LINE_SEPARATOR.length()).equals(LINE_SEPARATOR))
					return new PsiString(sb);
			}
			while(true);
		}
		catch(final java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	@Override
	public PsiBoolean psiSkip(final PsiInteger oCount)
		throws PsiException
	{
		final long count=oCount.longValue();
		try
		{
			return PsiBoolean.valueOf(count==reader.skip(count));
		}
		catch(final IllegalArgumentException e)
		{
			throw new PsiRangeCheckException();
		}
		catch(final java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	@Override
	public PsiBoolean psiReady()
		throws PsiException
	{
		try
		{
			return PsiBoolean.valueOf(reader.ready());
		}
		catch(final java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	@Override
	public void psiClose()
		throws PsiException
	{
		try
		{
			reader.close();
		}
		catch(final java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	@Override
	public void psiReset()
		throws PsiException
	{
		try
		{
			reader.reset();
		}
		catch(final java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	public static final String LINE_SEPARATOR=System.getProperty("line.separator");

	protected java.io.Reader reader;
}
