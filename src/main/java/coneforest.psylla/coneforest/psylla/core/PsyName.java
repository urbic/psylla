package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of {@code name}.
*/
@Type("name")
public final class PsyName
	extends PsyString
{
	/**
	*	Constructs a new {@code name} object with the given name.
	*
	*	@param cs the name.
	*/
	public PsyName(final CharSequence cs)
	{
		super(cs);
		string.intern();
	}

	public static PsyName parseLiteral(final String image)
	{
		return new PsyName(image);
	}

	@Override
	public void execute(final PsyContext oContext)
	{
		try
		{
			oContext.psyLoad(this).invoke(oContext);
		}
		catch(final PsyErrorException e)
		{
			e.setEmitter(this);
			final var ostack=oContext.operandStack();
			final var estack=oContext.executionStack();
			final var dstack=oContext.dictStack();
			e.setStacks(ostack, estack, dstack);
			e.invoke(oContext);
		}
	}

	@Override
	public void invoke(final PsyContext oContext)
	{
		execute(oContext);
	}

	@Override
	public String toSyntaxString()
	{
		return stringValue();
	}
}
