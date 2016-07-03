package coneforest.psi.core;

/**
*	A representation of Ψ-{@code numeric}, an abstraction of complex and real
*	numbers.
*/
public interface PsiNumeric
	extends
		PsiAtomic,
		PsiArithmetic<PsiNumeric>
{
	/**
	 *	@return a string {@code "numeric"}.
	 */
	@Override
	default public String typeName()
	{
		return "numeric";
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
	public PsiRealNumeric psiAbs();

	/**
	 *	Returns a Ψ-{@code numeric} real part of this object.
	 *
	 *	@return a Ψ-{@code numeric} real part.
	 */
	public PsiRealNumeric psiRealPart();

	/**
	 *	Returns a Ψ-{@code numeric} imaginary part of this object.
	 *
	 *	@return a Ψ-{@code numeric} imaginary part.
	 */
	public PsiRealNumeric psiImagPart();

	/**
	 *	Returns a Ψ-{@code numeric} representing the complex argument of this
	 *	object. The argument belongs to the range (−π; π].
	 *
	 *	@return a Ψ-{@code numeric} imaginary part.
	 *	@throws PsiUndefinedResultException when this object represents a zero
	 *	value.
	 */
	public PsiRealNumeric psiArg()
		throws PsiUndefinedResultException;

	/**
	 *	Returns a Ψ-{@code numeric} representing the complex conjugation
	 *	of this object.
	 *
	 *	@return a Ψ-{@code numeric} conjugate of this number.
	 */
	public PsiNumeric psiConjugate();

	default public PsiNumeric psiPow(final PsiNumeric oNumeric)
		throws PsiException
	{
		if(psiIsZero().booleanValue() && oNumeric.psiNotZero().booleanValue())
			return this;
		return psiLog().psiMul(oNumeric).psiExp();
	}

	/**
	 *	Returns a Ψ-{@code numeric} representing the exponent of this object.
	 *
	 *	@return a Ψ-{@code numeric} exponent of this number.
	 */
	public PsiNumeric psiExp();

	/**
	 *	Returns a Ψ-{@code numeric} representing the cosine of this object.
	 *
	 *	@return a Ψ-{@code numeric} cosine of this number.
	 */
	public PsiNumeric psiCos();

	/**
	 *	Returns a Ψ-{@code numeric} representing the sine of this object.
	 *
	 *	@return a Ψ-{@code numeric} sine of this number.
	 */
	public PsiNumeric psiSin();

	/**
	 *	Returns a Ψ-{@code numeric} representing the tangent of this object.
	 *
	 *	@return a Ψ-{@code numeric} tangent of this number.
	 */
	public PsiNumeric psiTan();

	/**
	 *	Returns a Ψ-{@code numeric} representing the natural logarithm of this
	 *	object.
	 *
	 *	@return a Ψ-{@code numeric} logarithm.
	 *	@throws PsiUndefinedResultException when this object represents a zero
	 *	value.
	 */
	public PsiNumeric psiLog()
		throws PsiUndefinedResultException;

	default public PsiNumeric psiAcos()
		throws PsiUndefinedResultException
	{
		return psiAdd(PsiComplex.ONE.psiSub(psiMul(this)).psiSqrt().psiMul(PsiComplex.I))
				.psiLog().psiMul(PsiComplex.MINUS_I);
	}

	default public PsiNumeric psiAsin()
		throws PsiUndefinedResultException
	{
		return new PsiComplex(Math.PI/2.D).psiSub(psiAcos());
	}

	/**
	 *	Returns a Ψ-{@code numeric} representing the arc tangent of this
	 *	object.
	 *
	 *	@return a Ψ-{@code numeric} arcc tangent.
	 *	@throws PsiUndefinedResultException when this object represents an
	 *	unadmissible value for arc tangent.
	 */
	public PsiNumeric psiAtan()
		throws PsiUndefinedResultException;

	/**
	 *	Returns a Ψ-{@code numeric} representing the square root of this
	 *	object.
	 *
	 *	@return a Ψ-{@code numeric} square root of this number.
	 */
	public PsiNumeric psiSqrt();

	/**
	 *	Returns a Ψ-{@code numeric} representing the cubic root of this object.
	 *
	 *	@return a Ψ-{@code numeric} cubic root of this number.
	 */
	public PsiNumeric psiCbrt();

	/**
	 *	Returns a Ψ-{@code numeric} representing the hyperbolic cosine of this
	 *	object.
	 *
	 *	@return a Ψ-{@code numeric} hyperbolic cosine of this number.
	 */
	public PsiNumeric psiCosh();

	/**
	 *	Returns a Ψ-{@code numeric} representing the hyperbolic sine of this
	 *	object.
	 *
	 *	@return a Ψ-{@code numeric} hyperbolic sine of this number.
	 */
	public PsiNumeric psiSinh();

	/**
	 *	Returns a Ψ-{@code numeric} representing the hyperbolic tangent of this
	 *	object.
	 *
	 *	@return a Ψ-{@code numeric} hyperbolic tangent of this number.
	 */
	public PsiNumeric psiTanh();

	@Override
	default public PsiBoolean psiEq(final PsiObject o)
	{
		return PsiBoolean.valueOf(o instanceof PsiNumeric
				&& psiRealPart().psiEq(((PsiNumeric)o).psiRealPart()).booleanValue()
				&& psiImagPart().psiEq(((PsiNumeric)o).psiImagPart()).booleanValue());
	}
}
