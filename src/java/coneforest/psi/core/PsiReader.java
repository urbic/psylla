package coneforest.psi.core;
import coneforest.psi.*;

public class PsiReader
	implements
		PsiCloseable,
		PsiEvaluable,
		PsiReadable,
		PsiResettable
{
	public PsiReader()
	{
	}

	public PsiReader(java.io.Reader reader)
	{
		setReader(reader);
	}

	public PsiReader(java.io.InputStream is)
	{
		this(new java.io.InputStreamReader(is));
	}

	@Override
	public void eval(Interpreter interpreter)
		throws PsiException
	{
		interpreter.interpret(this);
	}

	@Override
	public String getTypeName()
	{
		return "reader";
	}

	public void setReader(java.io.Reader reader)
	{
		this.reader=reader;
	}

	public java.io.Reader getReader()
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
		catch(java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	@Override
	public PsiString psiReadString(PsiInteger oCount)
		throws PsiException
	{
		final long count=oCount.longValue();
		if(count<=0)
			throw new PsiRangeCheckException();
		if(count>Integer.MAX_VALUE)
			throw new PsiLimitCheckException();
		try
		{
			/*
			java.nio.CharBuffer buffer=java.nio.CharBuffer.allocate((int)countValue);
			reader.read(buffer);
			buffer.flip();
			return new PsiString(buffer.toString());
			*/
			char[] buffer=new char[(int)count];
			reader.read(buffer);
			return new PsiString(new String(buffer));
		}
		catch(OutOfMemoryError e)
		{
			throw new PsiLimitCheckException();
		}
		catch(java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	@Override
	public PsiString psiReadLine(PsiStringy eol)
		throws PsiException
	{
		String eolString=eol.stringValue();
		StringBuilder sb=new StringBuilder();

		try
		{
			do
			{
				int c=reader.read();
				if(c==-1)
					return new PsiString(sb);
				sb.append((char)c);
				if(sb.substring(sb.length()-eolString.length()).equals(eolString))
					return new PsiString(sb);
			}
			while(true);
		}
		catch(java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	@Override
	public PsiBoolean psiSkip(PsiInteger oCount)
		throws PsiException
	{
		long count=oCount.longValue();
		try
		{
			return PsiBoolean.valueOf(count==reader.skip(count));
		}
		catch(IllegalArgumentException e)
		{
			throw new PsiRangeCheckException();
		}
		catch(java.io.IOException e)
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
		catch(java.io.IOException e)
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
		catch(java.io.IOException e)
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
		catch(java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	private java.io.Reader reader;
}
