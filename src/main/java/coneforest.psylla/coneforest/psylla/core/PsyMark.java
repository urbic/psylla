package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of {@code mark}.
*/
@Type("mark")
public final class PsyMark
	implements PsyAtomic
{
	private PsyMark()
	{
	}

	@Override
	public PsyBoolean psyEq(final PsyObject o)
	{
		return PsyBoolean.of(o==MARK);
	}

	/**
	*	The sole {@code mark} object.
	*/
	public static final PsyMark MARK=new PsyMark();
}
