package coneforest.psylla.core;

import coneforest.psylla.*;

/**
*	The representation of {@code numeric}, an abstraction of complex and real numbers.
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
	*	Returns the {@code numeric} absolute value of this object.
	*
	*	@return the {@code numeric} absolute value.
	*/
	public PsyRealNumeric psyAbs();


	/**
	*	Returns the {@code numeric} signum of this object.
	*
	*	@return the {@code numeric} signum.
	*/
	public PsyNumeric psySignum();

	public PsyNumeric psyPow(final PsyNumeric oNumeric);

	/**
	*	Returns the {@code numeric} exponent of this object.
	*
	*	@return the {@code numeric} exponent.
	*/
	public PsyNumeric psyExp();

	/**
	*	Returns the {@code numeric} cosine of this object.
	*
	*	@return the {@code numeric} cosine.
	*/
	public PsyNumeric psyCos();

	/**
	*	Returns the {@code numeric} sine of this object.
	*
	*	@return the {@code numeric} sine.
	*/
	public PsyNumeric psySin();

	/**
	*	Returns the {@code numeric} tangent of this object.
	*
	*	@return the {@code numeric} tangent.
	*/
	public PsyNumeric psyTan();

	/**
	*	Returns the {@code numeric} natural logarithm of this object.
	*
	*	@return the {@code numeric} natural logarithm.
	*/
	public PsyNumeric psyLog();

	/**
	*	Returns the {@code numeric} arc cosine of this object.
	*
	*	@return the {@code numeric} arc cosine.
	*/
	public PsyNumeric psyAcos();

	/**
	*	Returns the {@code numeric} arc sine of this object.
	*
	*	@return the {@code numeric} arc sine.
	*/
	public PsyNumeric psyAsin();

	/**
	*	Returns the {@code numeric} arc tangent of this object.
	*
	*	@return the {@code numeric} arc tangent.
	*/
	public PsyNumeric psyAtan();

	/**
	*	Returns the {@code numeric} square root of this object.
	*
	*	@return the {@code numeric} square root.
	*/
	public PsyNumeric psySqrt();

	/**
	*	Returns the {@code numeric} cubic root of this object.
	*
	*	@return the {@code numeric} cubic root.
	*/
	public PsyNumeric psyCbrt();

	/**
	*	Returns the {@code numeric} hyperbolic cosine of this object.
	*
	*	@return the {@code numeric} hyperbolic cosine.
	*/
	public PsyNumeric psyCosh();

	/**
	*	Returns the {@code numeric} hyperbolic sine of this object.
	*
	*	@return the {@code numeric} hyperbolic sine.
	*/
	public PsyNumeric psySinh();

	/**
	*	Returns the {@code numeric} hyperbolic tangent of this object.
	*
	*	@return the {@code numeric} hyperbolic tangent.
	*/
	public PsyNumeric psyTanh();

	/**
	*	Context action of the {@code abs} operator.
	*/
	@OperatorType("abs")
	public static final ContextAction PSY_ABS
		=ContextAction.<PsyNumeric>ofFunction(PsyNumeric::psyAbs);

	/**
	*	Context action of the {@code acos} operator.
	*/
	@OperatorType("acos")
	public static final ContextAction PSY_ACOS
		=ContextAction.<PsyNumeric>ofFunction(PsyNumeric::psyAcos);

	/**
	*	Context action of the {@code asin} operator.
	*/
	@OperatorType("asin")
	public static final ContextAction PSY_ASIN
		=ContextAction.<PsyNumeric>ofFunction(PsyNumeric::psyAsin);

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
