package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of {@code multiplicative}, a type of object that is an operand of
*	multiplicative operation. This interface declares methods for multiplication, division.
*
*	@param <T> a type of the second operand at binary operation.
*/
@Type("multiplicative")
public interface PsyMultiplicative<T extends PsyMultiplicative<T>>
	extends PsyObject
{
	/**
	*	Context action of the {@code div} operator.
	*/
	@SuppressWarnings({"unchecked", "rawtypes"})
	@OperatorType("div")
	public static final ContextAction PSY_DIV
		=ContextAction.<PsyMultiplicative, PsyMultiplicative>ofBiFunction(PsyMultiplicative::psyDiv);

	/**
	*	Context action of the {@code mul} operator.
	*/
	@SuppressWarnings({"unchecked", "rawtypes"})
	@OperatorType("mul")
	public static final ContextAction PSY_MUL
		=ContextAction.<PsyMultiplicative, PsyMultiplicative>ofBiFunction(PsyMultiplicative::psyMul);

	/**
	*	Context action of the {@code reciprocal} operator.
	*/
	@SuppressWarnings("rawtypes")
	@OperatorType("reciprocal")
	public static final ContextAction PSY_RECIPROCAL
		=ContextAction.<PsyMultiplicative>ofFunction(PsyMultiplicative::psyReciprocal);

	/**
	*	{@return the multiplicative inverse of this object}
	*
	*	@throws PsyUndefinedResultException if this object is zero.
	*/
	public T psyReciprocal()
		throws PsyUndefinedResultException;

	/**
	*	{@return a result of arithmetic multiplication of this number by given object}
	*
	*	@param oMultiplicative a given number.
	*/
	public T psyMul(final T oMultiplicative);

	/**
	*	{@return a result of arithmetic division of this object by given object}
	*
	*	@param oMultiplicative a given object.
	*	@throws PsyUndefinedResultException when the result of division is not defined.
	*/
	public T psyDiv(final T oMultiplicative)
		throws PsyUndefinedResultException;
}
