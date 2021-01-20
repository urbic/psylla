package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of Ψ-{@code numeric}, an abstraction of complex and real
*	numbers.
*/
@coneforest.psylla.Type("numeric")
public interface PsyNumeric
	extends
		PsyAtomic,
		PsyArithmetic<PsyNumeric>
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
	public PsyRealNumeric psyAbs();

	public PsyNumeric psyPow(final PsyNumeric oNumeric)
		throws PsyException;

	/**
	*	Returns a Ψ-{@code numeric} representing the exponent of this object.
	*
	*	@return a Ψ-{@code numeric} exponent.
	*/
	public PsyNumeric psyExp();

	/**
	*	Returns a Ψ-{@code numeric} representing the cosine of this object.
	*
	*	@return a Ψ-{@code numeric} cosine.
	*/
	public PsyNumeric psyCos();

	/**
	*	Returns a Ψ-{@code numeric} representing the sine of this object.
	*
	*	@return a Ψ-{@code numeric} sine.
	*/
	public PsyNumeric psySin();

	/**
	*	Returns a Ψ-{@code numeric} representing the tangent of this object.
	*
	*	@return a Ψ-{@code numeric} tangent.
	*/
	public PsyNumeric psyTan();

	/**
	*	Returns a Ψ-{@code numeric} representing the natural logarithm of this
	*	object.
	*
	*	@return a Ψ-{@code numeric} logarithm.
	*	value.
	*/
	public PsyNumeric psyLog();

	/**
	*	Returns a Ψ-{@code numeric} representing the arc cosine of this object.
	*
	*	@return a Ψ-{@code numeric} arc cosine.
	*/
	public PsyNumeric psyAcos();

	/**
	*	Returns a Ψ-{@code numeric} representing the arc sine of this object.
	*
	*	@return a Ψ-{@code numeric} arc sine.
	*/
	public PsyNumeric psyAsin();

	/**
	*	Returns a Ψ-{@code numeric} representing the arc tangent of this
	*	object.
	*
	*	@return a Ψ-{@code numeric} arcc tangent.
	*/
	public PsyNumeric psyAtan();

	/**
	*	Returns a Ψ-{@code numeric} representing the square root of this
	*	object.
	*
	*	@return a Ψ-{@code numeric} square root of this number.
	*/
	public PsyNumeric psySqrt();

	/**
	*	Returns a Ψ-{@code numeric} representing the cubic root of this object.
	*
	*	@return a Ψ-{@code numeric} cubic root of this number.
	*/
	public PsyNumeric psyCbrt();

	/**
	*	Returns a Ψ-{@code numeric} representing the hyperbolic cosine of this
	*	object.
	*
	*	@return a Ψ-{@code numeric} hyperbolic cosine of this number.
	*/
	public PsyNumeric psyCosh();

	/**
	*	Returns a Ψ-{@code numeric} representing the hyperbolic sine of this
	*	object.
	*
	*	@return a Ψ-{@code numeric} hyperbolic sine of this number.
	*/
	public PsyNumeric psySinh();

	/**
	*	Returns a Ψ-{@code numeric} representing the hyperbolic tangent of this
	*	object.
	*
	*	@return a Ψ-{@code numeric} hyperbolic tangent of this number.
	*/
	public PsyNumeric psyTanh();

}
