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

	public double realValue();

	public double imagValue();

	/**
	 *	Returns a Ψ-{@code numeric} absolute value of this object.
	 *
	 *	@return a Ψ-{@code numeric} absolute value.
	 */
	public PsiRealNumeric psiAbs();

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

	public PsiNumeric psiAcos()
		throws PsiUndefinedResultException;

	public PsiNumeric psiAsin()
		throws PsiUndefinedResultException;

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
	public PsiNumeric psiSqrt()
		throws PsiException;

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
}
