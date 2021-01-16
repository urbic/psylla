package coneforest.psylla.core;
import coneforest.psylla.*;

@Type("stringwriter")
public class PsyStringWriter
	extends PsyWriter
{
	public PsyStringWriter(final PsyString oString)
	{
		super(new StringBufferWriter(oString.getBuffer()));
	}

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity11<PsyString>("stringwriter", PsyStringWriter::new),
		};

}

class StringBufferWriter extends java.io.Writer
{
	StringBufferWriter(final StringBuilder buffer)
	{
		this.buffer=buffer;
	}

	public void close()
	{
	}

	public void flush()
	{
	}

	public void write(final char[] cbuf, final int off, final int len)
	{
		for(int i=off; i<off+len; i++)
			buffer.append(cbuf[i]);
	}

	private final StringBuilder buffer;

}
