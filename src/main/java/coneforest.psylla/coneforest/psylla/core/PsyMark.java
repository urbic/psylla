package coneforest.psylla.core;

/**
*	A representation of {@code mark}.
*/
@coneforest.psylla.Type("mark")
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
