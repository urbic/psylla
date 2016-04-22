package coneforest.psi.core;

/**
*	A representation of Ψ-{@code boolean} object.
*/
public class PsiBoolean
	implements
		PsiAtomic,
		PsiScalar<PsiBoolean>,
		PsiLogical<PsiBoolean>
{
	private PsiBoolean()
	{
	}

	/**
	*	@return a string {@code "boolean"}.
	*/
	@Override
	public String getTypeName()
	{
		return "boolean";
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
	public PsiBoolean psiNot()
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
	public PsiBoolean psiOr(final PsiBoolean oBoolean)
	{
		return PsiBoolean.valueOf(booleanValue() || oBoolean.booleanValue());
	}

	/**
	*	Returns a result of boolean conjunction of this object and given
	*	object.
	*
	*	@param oBoolean given object.
	*	@return a result.
	*/
	@Override
	public PsiBoolean psiAnd(final PsiBoolean oBoolean)
	{
		return PsiBoolean.valueOf(booleanValue() && oBoolean.booleanValue());
	}

	/**
	*	Returns a result of boolean exclusive disjunction of this object and
	*	given object.
	*
	*	@param oBoolean given object.
	*	@return a result.
	*/
	@Override
	public PsiBoolean psiXor(final PsiBoolean oBoolean)
	{
		return PsiBoolean.valueOf(booleanValue() ^ oBoolean.booleanValue());
	}

	/**
	*	Returns a result of equality test of this object and given object.
	*
	*	@return a result.
	*/
	@Override
	public PsiBoolean psiEq(final PsiObject o)
	{
		return PsiBoolean.valueOf(equals(o));
	}

	@Override
	public PsiInteger psiCmp(final PsiBoolean oBoolean)
	{
		return this==oBoolean? PsiInteger.ZERO:
				this==FALSE? PsiInteger.MINUS_ONE: PsiInteger.ONE;
	}

	@Override
	public PsiBoolean psiLt(final PsiBoolean oBoolean)
	{
		return PsiBoolean.valueOf(this==FALSE && oBoolean==TRUE);
	}

	@Override
	public PsiBoolean psiLe(final PsiBoolean oBoolean)
	{
		return PsiBoolean.valueOf(this==FALSE || this==oBoolean);
	}

	@Override
	public PsiBoolean psiGt(final PsiBoolean oBoolean)
	{
		return PsiBoolean.valueOf(this==TRUE && this==FALSE);
	}

	@Override
	public PsiBoolean psiGe(final PsiBoolean oBoolean)
	{
		return PsiBoolean.valueOf(this==TRUE || this==oBoolean);
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
	public static PsiBoolean valueOf(final boolean bool)
	{
		return bool? TRUE: FALSE;
	}

	/**
	*	A Ψ-{@code boolean} constant, representing false.
	*/
	public static final PsiBoolean FALSE=new PsiBoolean();

	/**
	*	A Ψ-{@code boolean} constant, representing true.
	*/
	public static final PsiBoolean TRUE=new PsiBoolean();
}
