package coneforest.psi;

public interface PsiComplexNumeric
	extends
		PsiAtomic,
		PsiArithmetic<PsiComplexNumeric>
{
	/**
	 *	@return a string {@code "complexnumeric"}.
	 */
	@Override
	default public String getTypeName()
	{
		return "complexnumeric";
	}

	/**
	 *	Returns a Ψ-{@code boolean} indicating whether this object represents a
	 *	zero value.
	 *
	 *	@return {@link PsiBoolean#TRUE} if this object represents a zero value,
	 *	and {@link PsiBoolean#FALSE} otherwise.
	 */
	public PsiBoolean psiIsZero();

	/**
	 *	Returns a Ψ-{@code boolean} indicating whether this object represents a
	 *	non-zero value.
	 *
	 *	@return {@link PsiBoolean#TRUE} if this object represents a non-zero value,
	 *	and {@link PsiBoolean#FALSE} otherwise.
	 */
	default public PsiBoolean psiNotZero()
	{
		return psiIsZero().psiNot();
	}

	/**
	 *	Returns a Ψ-{@code numeric} absolute value of this object.
	 *
	 *	@return a Ψ-{@code numeric} absolute value.
	 */
	public PsiNumeric psiAbs();

	/**
	 *	Returns a Ψ-{@code numeric} real part of this object.
	 *
	 *	@return a Ψ-{@code numeric} real part.
	 */
	public PsiNumeric psiRe();

	/**
	 *	Returns a Ψ-{@code numeric} imaginary part of this object.
	 *
	 *	@return a Ψ-{@code numeric} imaginary part.
	 */
	public PsiNumeric psiIm();

	/**
	 *	Returns a Ψ-{@code numeric} representing the complex argument of this
	 *	object. The argument belongs to the range (−π; π].
	 *
	 *	@return a Ψ-{@code numeric} imaginary part.
	 *	@throws PsiUndefinedResultException when this object represents a zero
	 *	value.
	 */
	public PsiNumeric psiArg()
		throws PsiUndefinedResultException;

	/**
	 *	Returns a Ψ-{@code complexnumeric} representing the complex conjugation
	 *	of this object.
	 *
	 *	@return a Ψ-{@code complexnumeric} conjugation.
	 */
	public PsiComplexNumeric psiConjugate();

	default public PsiComplexNumeric psiPow(PsiComplexNumeric cn)
		throws PsiException
	{
		if(psiIsZero().booleanValue() && cn.psiNotZero().booleanValue())
			return this;
		return cn.psiMul(psiLog()).psiExp();
	}

	/**
	 *	Returns a Ψ-{@code numeric} representing the exponent of this object.
	 *
	 *	@return a Ψ-{@code numeric} exponent.
	 */
	public PsiComplexNumeric psiExp();

	/**
	 *	Returns a Ψ-{@code numeric} representing the cosine of this object.
	 *
	 *	@return a Ψ-{@code numeric} cosine.
	 */
	public PsiComplexNumeric psiCos();

	/**
	 *	Returns a Ψ-{@code numeric} representing the sine of this object.
	 *
	 *	@return a Ψ-{@code numeric} sine.
	 */
	public PsiComplexNumeric psiSin();

	/**
	 *	Returns a Ψ-{@code numeric} representing the tangent of this object.
	 *
	 *	@return a Ψ-{@code numeric} tangent.
	 */
	public PsiComplexNumeric psiTan();

	/**
	 *	Returns a Ψ-{@code complexnumeric} representing the natural logarithm
	 *	of this object.
	 *
	 *	@return a Ψ-{@code complexnumeric} logarithm.
	 *	@throws PsiUndefinedResultException when this object represents a zero
	 *	value.
	 */
	public PsiComplexNumeric psiLog()
		throws PsiUndefinedResultException;

	/**
	 *	Returns a Ψ-{@code complexnumeric} representing the arc tangent
	 *	of this object.
	 *
	 *	@return a Ψ-{@code complexnumeric} arcc tangent.
	 *	@throws PsiUndefinedResultException when this object represents an
	 *	unadmissible value for arc tangent.
	 */
	public PsiComplexNumeric psiAtan()
		throws PsiUndefinedResultException;

	/**
	 *	Returns a Ψ-{@code complexnumeric} representing the square root of this
	 *	object.
	 *
	 *	@return a Ψ-{@code complexnumeric} square root.
	 */
	public PsiComplexNumeric psiSqrt();

	/**
	 *	Returns a Ψ-{@code complexnumeric} representing the cubic root of this
	 *	object.
	 *
	 *	@return a Ψ-{@code complexnumeric} cubic root.
	 */
	public PsiComplexNumeric psiCbrt();

	/**
	 *	Returns a Ψ-{@code numeric} representing the hyperbolic cosine of this
	 *	object.
	 *
	 *	@return a Ψ-{@code numeric} hyperbolic cosine.
	 */
	public PsiComplexNumeric psiCosh();

	/**
	 *	Returns a Ψ-{@code complexnumeric} representing the hyperbolic sine of this
	 *	object.
	 *
	 *	@return a Ψ-{@code complexnumeric} hyperbolic sine.
	 */
	public PsiComplexNumeric psiSinh();

	/**
	 *	Returns a Ψ-{@code complexnumeric} representing the hyperbolic tangent
	 *	of this object.
	 *
	 *	@return a Ψ-{@code complexnumeric} hyperbolic tangent.
	 */
	public PsiComplexNumeric psiTanh();

	@Override
	default public PsiBoolean psiEq(final PsiObject obj)
	{
		return PsiBoolean.valueOf(obj instanceof PsiComplexNumeric
				&& psiRe().psiEq(((PsiComplexNumeric)obj).psiRe()).booleanValue()
				&& psiIm().psiEq(((PsiComplexNumeric)obj).psiIm()).booleanValue());
	}
}
