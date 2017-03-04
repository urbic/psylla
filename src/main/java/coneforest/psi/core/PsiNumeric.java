package coneforest.psi.core;

/**
*	A representation of Ψ-{@code numeric}, an abstraction of complex and real
*	numbers.
*/
@coneforest.psi.Type("numeric")
public interface PsiNumeric
	extends
		PsiAtomic,
		PsiArithmetic<PsiNumeric>
{
	/**
	*	Returns the real part of this object.
	*
	*	@return the real part.
	*/
	public double realValue();

	/**
	*	Returns the real part of this object.
	*
	*	@return the imaginary part.
	*/
	public double imagValue();

	/**
	*	Returns a Ψ-{@code numeric} absolute value of this object.
	*
	*	@return a Ψ-{@code numeric} absolute value.
	*/
	public PsiRealNumeric psiAbs();

	public PsiNumeric psiPow(final PsiNumeric oNumeric)
		throws PsiException;

	/**
	*	Returns a Ψ-{@code numeric} representing the exponent of this object.
	*
	*	@return a Ψ-{@code numeric} exponent.
	*/
	public PsiNumeric psiExp();

	/**
	*	Returns a Ψ-{@code numeric} representing the cosine of this object.
	*
	*	@return a Ψ-{@code numeric} cosine.
	*/
	public PsiNumeric psiCos();

	/**
	*	Returns a Ψ-{@code numeric} representing the sine of this object.
	*
	*	@return a Ψ-{@code numeric} sine.
	*/
	public PsiNumeric psiSin();

	/**
	*	Returns a Ψ-{@code numeric} representing the tangent of this object.
	*
	*	@return a Ψ-{@code numeric} tangent.
	*/
	public PsiNumeric psiTan();

	/**
	*	Returns a Ψ-{@code numeric} representing the natural logarithm of this
	*	object.
	*
	*	@return a Ψ-{@code numeric} logarithm.
	*	value.
	*/
	public PsiNumeric psiLog();

	/**
	*	Returns a Ψ-{@code numeric} representing the arc cosine of this object.
	*
	*	@return a Ψ-{@code numeric} arc cosine.
	*/
	public PsiNumeric psiAcos();

	/**
	*	Returns a Ψ-{@code numeric} representing the arc sine of this object.
	*
	*	@return a Ψ-{@code numeric} arc sine.
	*/
	public PsiNumeric psiAsin();

	/**
	*	Returns a Ψ-{@code numeric} representing the arc tangent of this
	*	object.
	*
	*	@return a Ψ-{@code numeric} arcc tangent.
	*	@throws PsiUndefinedResultException when this object represents an
	*	unadmissible value for arc tangent.
	*/
	public PsiNumeric psiAtan();

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
}
