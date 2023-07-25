package coneforest.psylla.core;

import coneforest.psylla.*;

/**
*	A representation of {@code numeric}, an abstraction of complex and real
*	numbers.
*/
@Type("numeric")
public sealed interface PsyNumeric
	extends
		PsyAtomic,
		PsyArithmetic<PsyNumeric>
	permits
		PsyComplex,
		PsyRealNumeric
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


	/**
	*	Returns the signum of this object.
	*
	*	@return the {@code numeric} signum of this object.
	*/
	public PsyNumeric psySignum();

	public PsyNumeric psyPow(final PsyNumeric oNumeric);

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

	/**
	*	Context action of the {@code abs} operator.
	*/
	@OperatorType("abs")
	public static final ContextAction PSY_ABS
		=ContextAction.<PsyNumeric>ofFunction(PsyNumeric::psyAbs);

	/**
	*	Context action of the {@code scos} operator.
	*/
	@OperatorType("acos")
	public static final ContextAction PSY_ACOS
		=ContextAction.<PsyNumeric>ofFunction(PsyNumeric::psyAcos);

	/**
	*	Context action of the {@code atan} operator.
	*/
	@OperatorType("atan")
	public static final ContextAction PSY_ATAN
		=ContextAction.<PsyNumeric>ofFunction(PsyNumeric::psyAtan);

	/**
	*	Context action of the {@code cbrt} operator.
	*/
	@OperatorType("cbrt")
	public static final ContextAction PSY_CBRT
		=ContextAction.<PsyNumeric>ofFunction(PsyNumeric::psyCbrt);

	/**
	*	Context action of the {@code cos} operator.
	*/
	@OperatorType("cos")
	public static final ContextAction PSY_COS
		=ContextAction.<PsyNumeric>ofFunction(PsyNumeric::psyCos);

	/**
	*	Context action of the {@code cosh} operator.
	*/
	@OperatorType("cosh")
	public static final ContextAction PSY_COSH
		=ContextAction.<PsyNumeric>ofFunction(PsyNumeric::psyCosh);

	/**
	*	Context action of the {@code exp} operator.
	*/
	@OperatorType("exp")
	public static final ContextAction PSY_EXP
		=ContextAction.<PsyNumeric>ofFunction(PsyNumeric::psyExp);

	/**
	*	Context action of the {@code log} operator.
	*/
	@OperatorType("log")
	public static final ContextAction PSY_LOG
		=ContextAction.<PsyNumeric>ofFunction(PsyNumeric::psyLog);

	/**
	*	Context action of the {@code pow} operator.
	*/
	@OperatorType("pow")
	public static final ContextAction PSY_POW
		=ContextAction.<PsyNumeric, PsyNumeric>ofBiFunction(PsyNumeric::psyPow);

	/**
	*	Context action of the {@code signum} operator.
	*/
	@OperatorType("signum")
	public static final ContextAction PSY_SIGNUM
		=ContextAction.<PsyNumeric>ofFunction(PsyNumeric::psySignum);

	/**
	*	Context action of the {@code sin} operator.
	*/
	@OperatorType("sin")
	public static final ContextAction PSY_SIN
		=ContextAction.<PsyNumeric>ofFunction(PsyNumeric::psySin);

	/**
	*	Context action of the {@code sinh} operator.
	*/
	@OperatorType("sinh")
	public static final ContextAction PSY_SINH
		=ContextAction.<PsyNumeric>ofFunction(PsyNumeric::psySinh);

	/**
	*	Context action of the {@code sqrt} operator.
	*/
	@OperatorType("sqrt")
	public static final ContextAction PSY_SQRT
		=ContextAction.<PsyNumeric>ofFunction(PsyNumeric::psySqrt);

	/**
	*	Context action of the {@code tan} operator.
	*/
	@OperatorType("tan")
	public static final ContextAction PSY_TAN
		=ContextAction.<PsyNumeric>ofFunction(PsyNumeric::psyTan);

	/**
	*	Context action of the {@code tanh} operator.
	*/
	@OperatorType("tanh")
	public static final ContextAction PSY_TANH
		=ContextAction.<PsyNumeric>ofFunction(PsyNumeric::psyTanh);
}
