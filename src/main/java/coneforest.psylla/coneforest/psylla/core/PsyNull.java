package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of {@code null}, a type of a void placeholder. There is the only instance of
*	this class, {@link #NULL}.
*/
@Type("null")
public final class PsyNull
	implements PsyValue
{
	/**
	*	The sole {@code null} object.
	*/
	public static final PsyNull NULL=new PsyNull();

	private PsyNull()
	{
	}

	/**
	*	{@return a string {@code "null"}}
	*/
	@Override
	public String toSyntaxString()
	{
		return "null";
	}

	/**
	*	{@return a {@code boolean} indicating whether some other object is “equal to” this one}
	*	Return value is {@code true} if and only if other object has {@code name} type.
	*/
	@Override
	public PsyBoolean psyEq(final PsyObject o)
	{
		return PsyBoolean.of(o==NULL);
	}
}
