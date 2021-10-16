package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of {@code numeric}, an abstraction of complex and real
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
	*	Returns a {@code numeric} absolute value of this object.
	*
	*	@return a {@code numeric} absolute value.
	*/
	public PsyRealNumeric psyAbs();

	public PsyNumeric psyPow(final PsyNumeric oNumeric)
		throws PsyException;

	/**
	*	Returns a {@code numeric} representing the exponent of this object.
	*
	*	@return a {@code numeric} exponent.
	*/
	public PsyNumeric psyExp();

	/**
	*	Returns a {@code numeric} representing the cosine of this object.
	*
	*	@return a {@code numeric} cosine.
	*/
	public PsyNumeric psyCos();

	/**
	*	Returns a {@code numeric} representing the sine of this object.
	*
	*	@return a {@code numeric} sine.
	*/
	public PsyNumeric psySin();

	/**
	*	Returns a {@code numeric} representing the tangent of this object.
	*
	*	@return a {@code numeric} tangent.
	*/
	public PsyNumeric psyTan();

	/**
	*	Returns a {@code numeric} representing the natural logarithm of this
	*	object.
	*
	*	@return a {@code numeric} logarithm.
	*/
	public PsyNumeric psyLog();

	/**
	*	Returns a {@code numeric} representing the arc cosine of this object.
	*
	*	@return a {@code numeric} arc cosine.
	*/
	public PsyNumeric psyAcos();

	/**
	*	Returns a {@code numeric} representing the arc sine of this object.
	*
	*	@return a {@code numeric} arc sine.
	*/
	public PsyNumeric psyAsin();

	/**
	*	Returns a {@code numeric} representing the arc tangent of this object.
	*
	*	@return a {@code numeric} arc tangent.
	*/
	public PsyNumeric psyAtan();

	/**
	*	Returns a {@code numeric} representing the square root of this object.
	*
	*	@return a {@code numeric} square root of this number.
	*/
	public PsyNumeric psySqrt();

	/**
	*	Returns a {@code numeric} representing the cubic root of this object.
	*
	*	@return a {@code numeric} cubic root of this number.
	*/
	public PsyNumeric psyCbrt();

	/**
	*	Returns a {@code numeric} representing the hyperbolic cosine of this
	*	object.
	*
	*	@return a {@code numeric} hyperbolic cosine of this number.
	*/
	public PsyNumeric psyCosh();

	/**
	*	Returns a {@code numeric} representing the hyperbolic sine of this
	*	object.
	*
	*	@return a {@code numeric} hyperbolic sine of this number.
	*/
	public PsyNumeric psySinh();

	/**
	*	Returns a {@code numeric} representing the hyperbolic tangent of this
	*	object.
	*
	*	@return a {@code numeric} hyperbolic tangent of this number.
	*/
	public PsyNumeric psyTanh();

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity11<PsyNumeric>("abs", PsyNumeric::psyAbs),
			new PsyOperator.Arity11<PsyNumeric>("acos", PsyNumeric::psyAcos),
			new PsyOperator.Arity11<PsyNumeric>("asin", PsyNumeric::psyAsin),
			new PsyOperator.Arity11<PsyNumeric>("atan", PsyNumeric::psyAtan),
			new PsyOperator.Arity11<PsyNumeric>("cbrt", PsyNumeric::psyCbrt),
			new PsyOperator.Arity11<PsyNumeric>("cos", PsyNumeric::psyCos),
			new PsyOperator.Arity11<PsyNumeric>("cosh", PsyNumeric::psyCosh),
			new PsyOperator.Arity11<PsyNumeric>("exp", PsyNumeric::psyExp),
			new PsyOperator.Arity11<PsyNumeric>("iszero", PsyNumeric::psyIsZero),
			new PsyOperator.Arity11<PsyNumeric>("log", PsyNumeric::psyLog),
			new PsyOperator.Arity11<PsyNumeric>("nonzero", PsyNumeric::psyNotZero),
			new PsyOperator.Arity21<PsyNumeric, PsyNumeric>("pow", PsyNumeric::psyPow),
			new PsyOperator.Arity11<PsyNumeric>("sin", PsyNumeric::psySin),
			new PsyOperator.Arity11<PsyNumeric>("sinh", PsyNumeric::psySinh),
			new PsyOperator.Arity11<PsyNumeric>("sqrt", PsyNumeric::psySqrt),
			new PsyOperator.Arity11<PsyNumeric>("tan", PsyNumeric::psyTan),
			new PsyOperator.Arity11<PsyNumeric>("tanh", PsyNumeric::psyTanh),
		};
}
