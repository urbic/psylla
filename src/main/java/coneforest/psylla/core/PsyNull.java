package coneforest.psylla.core;

/**
*	A representation of {@code null}, a type of a void placeholder. There is
*	the only instance of this class, {@link #NULL}.
*/
@coneforest.psylla.Type("null")
public final class PsyNull
	implements PsyAtomic
{
	private PsyNull()
	{
	}

	/**
	*	@return a string {@code "null"}.
	*/
	@Override
	public String toSyntaxString()
	{
		return "null";
	}

	/**
	*	Returns a {@code boolean} indicating whether some other object is
	*	“equal to” this one. Return value is {@code true} if and only if other
	*	object has {@code name} type.
	*
	*	@return a result.
	*/
	@Override
	public PsyBoolean psyEq(final PsyObject o)
	{
		return PsyBoolean.valueOf(o==NULL);
	}

	/**
	*	The sole {@code null} object.
	*/
	public static final PsyNull NULL=new PsyNull();
}
