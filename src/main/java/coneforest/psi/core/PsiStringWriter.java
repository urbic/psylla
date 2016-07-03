package coneforest.psi.core;

public class PsiStringWriter
	extends PsiWriter
{
	public PsiStringWriter(final PsiString oString)
	{
		super(new StringBufferWriter(oString.getBuffer()));
	}
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
