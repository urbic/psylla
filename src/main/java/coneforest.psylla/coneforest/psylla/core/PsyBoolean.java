package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of {@code boolean}.
*/
@Type("boolean")
public final class PsyBoolean
	implements PsyLogical<PsyBoolean>, PsyScalar<PsyBoolean>, PsyValue
{
	private PsyBoolean()
	{
	}

	/**
	*	Returns a boolean value of this object.
	*
	*	@return a boolean value of this object.
	*/
	public boolean booleanValue()
	{
		return this==TRUE;
	}

	/**
	*	@return a string {@code false} or {@code true} depending on this object
	*	value.
	*/
	@Override
	public String toSyntaxString()
	{
		return "%"+booleanValue()+"%";
	}

	/**
	*	Returns a result of boolean negation of this object.
	*
	*	@return a result.
	*/
	@Override
	public PsyBoolean psyNot()
	{
		return booleanValue()? FALSE: TRUE;
	}

	/**
	*	Returns a result of boolean disjunction of this object and given
	*	object.
	*
	*	@param oBoolean given object.
	*	@return a result.
	*/
	@Override
	public PsyBoolean psyOr(final PsyBoolean oBoolean)
	{
		return PsyBoolean.of(booleanValue() || oBoolean.booleanValue());
	}

	/**
	*	Returns a result of boolean conjunction of this object and given
	*	object.
	*
	*	@param oBoolean given object.
	*	@return a result.
	*/
	@Override
	public PsyBoolean psyAnd(final PsyBoolean oBoolean)
	{
		return PsyBoolean.of(booleanValue() && oBoolean.booleanValue());
	}

	/**
	*	Returns a result of boolean exclusive disjunction of this object and
	*	given object.
	*
	*	@param oBoolean given object.
	*	@return a result.
	*/
	@Override
	public PsyBoolean psyXor(final PsyBoolean oBoolean)
	{
		return PsyBoolean.of(booleanValue() ^ oBoolean.booleanValue());
	}

	/**
	*	Returns a result of equality test of this object and given object.
	*
	*	@return a result.
	*/
	@Override
	public PsyBoolean psyEq(final PsyObject o)
	{
		return PsyBoolean.of(this==o);
	}

	@Override
	public int compareTo(final PsyBoolean oBoolean)
	{
		return this==oBoolean? 0: this==FALSE? -1: 1;
	}

	@Override
	public int hashCode()
	{
		return booleanValue()? 1231: 1237;
	}

	@Override
	public boolean equals(final Object object)
	{
		return this==object;
	}

	/**
	*	Returns a {@code boolean} representing the given boolean value.
	*
	*	@param bool a given value.
	*	@return a {@code boolean} object.
	*/
	public static PsyBoolean of(final boolean bool)
	{
		return bool? TRUE: FALSE;
	}

	/**
	*	A {@code boolean} constant, representing false.
	*/
	public static final PsyBoolean FALSE=new PsyBoolean();

	/**
	*	A {@code boolean} constant, representing true.
	*/
	public static final PsyBoolean TRUE=new PsyBoolean();
}
