package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The {@code mark} object is used as a mark on the operand stack. There is the only instance of
*	this class, {@link #MARK}.
*/
@Type("mark")
public final class PsyMark
	implements PsyObject
{
	/**
	*	The sole {@code mark} object.
	*/
	public static final PsyMark MARK=new PsyMark();

	private PsyMark()
	{
	}

	@Override
	public PsyBoolean psyEq(final PsyObject o)
	{
		return PsyBoolean.of(this==o);
	}
}
