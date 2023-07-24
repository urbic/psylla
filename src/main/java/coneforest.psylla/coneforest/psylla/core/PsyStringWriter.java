package coneforest.psylla.core;

import coneforest.psylla.*;
import java.io.Writer;

/**
*	A representation of {@code stringwriter}.
*/
@Type("stringwriter")
public class PsyStringWriter
	extends PsyWriter
{
	public PsyStringWriter(final PsyString oString)
	{
		super(new StringBufferWriter(oString.getBuffer()));
	}

	private static class StringBufferWriter
		extends Writer
	{
		StringBufferWriter(final StringBuilder buffer)
		{
			this.buffer=buffer;
		}

		@Override
		public void close()
		{
		}

		@Override
		public void flush()
		{
		}

		@Override
		public void write(final char[] cbuf, final int off, final int len)
		{
			for(int i=off; i<off+len; i++)
				buffer.append(cbuf[i]);
		}

		private final StringBuilder buffer;
	}

	/**
	*	Context action of the {@code stringwriter} operator.
	*/
	@OperatorType("stringwriter")
	public static final ContextAction PSY_STRINGWRITER
		=ContextAction.<PsyString>ofFunction(PsyStringWriter::new);
}
