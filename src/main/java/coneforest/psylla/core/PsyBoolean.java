package coneforest.psylla.core;

/**
*	A representation of Ψ-{@code boolean} object.
*/
@coneforest.psylla.Type("boolean")
public class PsyBoolean
	implements
		PsyAtomic,
		PsyScalar<PsyBoolean>,
		PsyLogical<PsyBoolean>
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
		return ""+booleanValue();
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
		return PsyBoolean.valueOf(booleanValue() || oBoolean.booleanValue());
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
		return PsyBoolean.valueOf(booleanValue() && oBoolean.booleanValue());
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
		return PsyBoolean.valueOf(booleanValue() ^ oBoolean.booleanValue());
	}

	/**
	*	Returns a result of equality test of this object and given object.
	*
	*	@return a result.
	*/
	@Override
	public PsyBoolean psyEq(final PsyObject o)
	{
		return PsyBoolean.valueOf(equals(o));
	}

	@Override
	public PsyInteger psyCmp(final PsyBoolean oBoolean)
	{
		return this==oBoolean? PsyInteger.ZERO:
				this==FALSE? PsyInteger.MINUS_ONE: PsyInteger.ONE;
	}

	@Override
	public PsyBoolean psyLt(final PsyBoolean oBoolean)
	{
		return PsyBoolean.valueOf(this==FALSE && oBoolean==TRUE);
	}

	@Override
	public PsyBoolean psyLe(final PsyBoolean oBoolean)
	{
		return PsyBoolean.valueOf(this==FALSE || this==oBoolean);
	}

	@Override
	public PsyBoolean psyGt(final PsyBoolean oBoolean)
	{
		return PsyBoolean.valueOf(this==TRUE && this==FALSE);
	}

	@Override
	public PsyBoolean psyGe(final PsyBoolean oBoolean)
	{
		return PsyBoolean.valueOf(this==TRUE || this==oBoolean);
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
	*	Returns a Ψ-{@code boolean} representing the given boolean value.
	*
	*	@param bool a given value.
	*	@return a Ψ-{@code boolean} object.
	*/
	public static PsyBoolean valueOf(final boolean bool)
	{
		return bool? TRUE: FALSE;
	}

	/**
	*	A Ψ-{@code boolean} constant, representing false.
	*/
	public static final PsyBoolean FALSE=new PsyBoolean();

	/**
	*	A Ψ-{@code boolean} constant, representing true.
	*/
	public static final PsyBoolean TRUE=new PsyBoolean();
}
