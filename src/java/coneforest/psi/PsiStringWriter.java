package coneforest.psi;

public class PsiStringWriter extends PsiWriter
{
	public PsiStringWriter(PsiString string)
	{
		super(new StringBufferWriter(string.getBuffer()));
	}
}

class StringBufferWriter extends java.io.Writer
{
	StringBufferWriter(StringBuilder buffer)
	{
		this.buffer=buffer;
	}

	public void close()
	{
	}

	public void flush()
	{
	}

	public void write(char[] cbuf, int off, int len)
	{
		for(int i=off; i<off+len; i++)
			buffer.append(cbuf[i]);
	}

	private StringBuilder buffer;
}
