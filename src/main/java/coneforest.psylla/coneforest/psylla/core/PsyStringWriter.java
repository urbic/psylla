package coneforest.psylla.core;

import coneforest.psylla.runtime.*;
import java.io.Writer;

/**
*	The representation of {@code stringwriter}.
*/
@Type("stringwriter")
public class PsyStringWriter
	extends PsyWriter
{
	/**
	*	Context action of the {@code stringwriter} operator.
	*/
	@OperatorType("stringwriter")
	public static final ContextAction PSY_STRINGWRITER
		=ContextAction.<PsyStringBuffer>ofFunction(PsyStringWriter::new);

	/**
	*	Creates a new {@code stringwriter} object.
	*
	*	@param oString a {@code string} to wtite to.
	*/
	public PsyStringWriter(final PsyStringBuffer oString)
	{
		super(new StringBufferWriter(oString.getBuffer()));
	}

	private static class StringBufferWriter
		extends Writer
	{
		private final StringBuilder buffer;

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
	}
}
