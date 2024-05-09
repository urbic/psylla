package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of {@code scalar}, a type bringing total ordering to implementing Psylla
*	type. This interface declares methods for comparison.
*
*	@param <T> a type of the second operand at binary comparison operation.
*/
@Type("scalar")
public interface PsyScalar<T extends PsyScalar>
	extends PsyObject
{

	/**
	*	Returns a {@code boolean} object representing the result of “less” comparison of this object
	*	and a given object.
	*
	*	@param oScalar an object with which this object is compared.
	*	@return a {@code boolean} value indicating if this object is less than given object.
	*/
	public PsyBoolean psyLt(final T oScalar);

	/**
	*	Returns a {@code boolean} object representing the result of “less or equal” comparison of
	*	this object and a given object.
	*
	*	@param oScalar an object with which this object is compared.
	*	@return a {@code boolean} value indicating if this object is less than or equal to given
	*	object.
	*/
	public PsyBoolean psyLe(final T oScalar);

	/**
	*	Returns a {@code boolean} object representing the result of “greater” comparison of this
	*	object and a given object.
	*
	*	@param oScalar an object with which this object is compared.
	*	@return a {@code boolean} result of comparison.
	*/
	public PsyBoolean psyGt(final T oScalar);

	/**
	*	Returns a {@code boolean} object representing the result of “greater or equal” comparison of
	*	this object and a given object.
	*
	*	@param oScalar an object with which this object is compared.
	*	@return a {@code boolean} result of comparison.
	*/
	public PsyBoolean psyGe(final T oScalar);

	/**
	*	Compares this object against given object and returns an {@code integer} indicating the
	*	result of the comparison. Returns negative value if this object is less than given one, zero
	*	if this object is equal to given one, and positive value if this object is greater than
	*	given one.
	*
	*	@param oScalar an object with which this object is compared.
	*	@return a result of the comparison.
	*/
	public PsyInteger psyCmp(final T oScalar);

	/**
	*	Returns the minimum of this {@code scalar} and given {@code scalar}.
	*
	*	@param oScalar the given {@code scalar}.
	*	@return the minimum of this {@code scalar} and given {@code scalar}.
	*/
	default public PsyScalar psyMin(final T oScalar)
	{
		return psyLt(oScalar).booleanValue()? this: oScalar;
	}

	/**
	*	Returns the maximum of this {@code scalar} and given {@code scalar}.
	*
	*	@param oScalar the given {@code scalar}.
	*	@return the maximum of this {@code scalar} and given {@code scalar}.
	*/
	default public PsyScalar psyMax(final T oScalar)
	{
		return psyGt(oScalar).booleanValue()? this: oScalar;
	}

	/**
	*	Context action of the {@code cmp} operator.
	*/
	@OperatorType("cmp")
	public static final ContextAction PSY_CMP
		=ContextAction.<PsyScalar, PsyScalar>ofBiFunction(PsyScalar::psyCmp);

	/**
	*	Context action of the {@code ge} operator.
	*/
	@OperatorType("ge")
	public static final ContextAction PSY_GE
		=ContextAction.<PsyScalar, PsyScalar>ofBiFunction(PsyScalar::psyGe);

	/**
	*	Context action of the {@code gt} operator.
	*/
	@OperatorType("gt")
	public static final ContextAction PSY_GT
		=ContextAction.<PsyScalar, PsyScalar>ofBiFunction(PsyScalar::psyGt);

	/**
	*	Context action of the {@code le} operator.
	*/
	@OperatorType("le")
	public static final ContextAction PSY_LE
		=ContextAction.<PsyScalar, PsyScalar>ofBiFunction(PsyScalar::psyLe);

	/**
	*	Context action of the {@code lt} operator.
	*/
	@OperatorType("lt")
	public static final ContextAction PSY_LT
		=ContextAction.<PsyScalar, PsyScalar>ofBiFunction(PsyScalar::psyLt);

	/**
	*	Context action of the {@code max} operator.
	*/
	@OperatorType("max")
	public static final ContextAction PSY_MAX
		=ContextAction.<PsyScalar, PsyScalar>ofBiFunction(PsyScalar::psyMax);

	/**
	*	Context action of the {@code min} operator.
	*/
	@OperatorType("min")
	public static final ContextAction PSY_MIN
		=ContextAction.<PsyScalar, PsyScalar>ofBiFunction(PsyScalar::psyMin);
}
