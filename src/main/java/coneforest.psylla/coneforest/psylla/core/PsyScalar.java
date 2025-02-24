package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of {@code scalar}, a type bringing total ordering to implementing Psylla
*	type. This interface declares methods for comparison.
*
*	@param <T> a type of the second operand at binary comparison operation.
*/
@Type("scalar")
public interface PsyScalar<T extends PsyScalar<T>>
	extends PsyObject, Comparable<T>
{
	/**
	*	Context action of the {@code cmp} operator.
	*/
	@SuppressWarnings({"unchecked", "rawtypes"})
	@OperatorType("cmp")
	public static final ContextAction PSY_CMP
		=ContextAction.<PsyScalar, PsyScalar>ofBiFunction(PsyScalar::psyCmp);

	/**
	*	Context action of the {@code ge} operator.
	*/
	@SuppressWarnings({"unchecked", "rawtypes"})
	@OperatorType("ge")
	public static final ContextAction PSY_GE
		=ContextAction.<PsyScalar, PsyScalar>ofBiFunction(PsyScalar::psyGe);

	/**
	*	Context action of the {@code gt} operator.
	*/
	@SuppressWarnings({"unchecked", "rawtypes"})
	@OperatorType("gt")
	public static final ContextAction PSY_GT
		=ContextAction.<PsyScalar, PsyScalar>ofBiFunction(PsyScalar::psyGt);

	/**
	*	Context action of the {@code le} operator.
	*/
	@SuppressWarnings({"unchecked", "rawtypes"})
	@OperatorType("le")
	public static final ContextAction PSY_LE
		=ContextAction.<PsyScalar, PsyScalar>ofBiFunction(PsyScalar::psyLe);

	/**
	*	Context action of the {@code lt} operator.
	*/
	@SuppressWarnings({"unchecked", "rawtypes"})
	@OperatorType("lt")
	public static final ContextAction PSY_LT
		=ContextAction.<PsyScalar, PsyScalar>ofBiFunction(PsyScalar::psyLt);

	/**
	*	Context action of the {@code max} operator.
	*/
	@SuppressWarnings({"unchecked", "rawtypes"})
	@OperatorType("max")
	public static final ContextAction PSY_MAX
		=ContextAction.<PsyScalar, PsyScalar>ofBiFunction(PsyScalar::psyMax);

	/**
	*	Context action of the {@code min} operator.
	*/
	@SuppressWarnings({"unchecked", "rawtypes"})
	@OperatorType("min")
	public static final ContextAction PSY_MIN
		=ContextAction.<PsyScalar, PsyScalar>ofBiFunction(PsyScalar::psyMin);

	/**
	*	Compares this {@code scalar} with the specified {@code scalar} for order. Returns a negative
	*	integer, zero, or a positive integer as this object is less than, equal to, or greater than
	*	the specified {@code scalar}.
	*
	*	@param oScalar the {@code scalar} to be compared.
	*	@return a negative integer, zero, or a positive integer as this object is less than, equal
	*	to, or greater than the specified.
	*/
	@Override
	public int compareTo(final T oScalar);

	/**
	*	{@return a {@code boolean} object representing the result of “less” comparison of this
	*	{@code scalar} and a given {@code scalar}}
	*
	*	@param oScalar an object with which this object is compared.
	*/
	public default PsyBoolean psyLt(final T oScalar)
	{
		return PsyBoolean.of(compareTo(oScalar)<0);
	}

	/**
	*	{@return a {@code boolean} object representing the result of “less or equal” comparison of
	*	this {@code scalar} and a given {@code scalar}}
	*
	*	@param oScalar an object with which this object is compared.
	*/
	public default PsyBoolean psyLe(final T oScalar)
	{
		return PsyBoolean.of(compareTo(oScalar)<=0);
	}

	/**
	*	{@return a {@code boolean} object representing the result of “greater” comparison of this
	*	{@code scalar} and a given {@code scalar}}
	*
	*	@param oScalar an object with which this object is compared.
	*/
	public default PsyBoolean psyGt(final T oScalar)
	{
		return PsyBoolean.of(compareTo(oScalar)>0);
	}

	/**
	*	{@return a {@code boolean} object representing the result of “greater or equal” comparison
	*	of this {@code scalar} and a given {@code scalar}}
	*
	*	@param oScalar an object with which this object is compared.
	*/
	public default PsyBoolean psyGe(final T oScalar)
	{
		return PsyBoolean.of(compareTo(oScalar)>=0);
	}

	/**
	*	Compares this {@code scalar} against given {@code scalar} and returns an {@code integer}
	*	indicating the result of the comparison. Returns negative value if this object is less than
	*	given one, zero if this object is equal to given one, and positive value if this object is
	*	greater than given one.
	*
	*	@param oScalar an object with which this object is compared.
	*	@return a result of the comparison.
	*/
	public default PsyInteger psyCmp(final T oScalar)
	{
		return PsyInteger.of(compareTo(oScalar));
	}

	/**
	*	{@return the minimum of this {@code scalar} and given {@code scalar}}
	*
	*	@param oScalar the given {@code scalar}.
	*/
	public default PsyScalar<T> psyMin(final T oScalar)
	{
		return compareTo(oScalar)<0? this: oScalar;
	}

	/**
	*	{@return the maximum of this {@code scalar} and given {@code scalar}}
	*
	*	@param oScalar the given {@code scalar}.
	*/
	public default PsyScalar<T> psyMax(final T oScalar)
	{
		return compareTo(oScalar)>0? this: oScalar;
	}
}
