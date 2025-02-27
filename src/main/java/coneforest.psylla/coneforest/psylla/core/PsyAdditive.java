package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of {@code additive}, a type of object that is an operand of additive
*	operation. This interface declares methods for addition, subtraction and negation.
*
*	@param <T> a type of the second operand at binary operation.
*/
@Type("additive")
public interface PsyAdditive<T extends PsyAdditive<T>>
	extends PsyObject
{
	/**
	*	Context action of the {@code add} operator.
	*/
	@SuppressWarnings({"unchecked", "rawtypes"})
	@OperatorType("add")
	public static final ContextAction PSY_ADD
		=ContextAction.<PsyAdditive, PsyAdditive>ofBiFunction(PsyAdditive::psyAdd);

	/**
	*	Context action of the {@code iszero} operator.
	*/
	@SuppressWarnings("rawtypes")
	@OperatorType("iszero")
	public static final ContextAction PSY_ISZERO
		=ContextAction.<PsyAdditive>ofFunction(PsyAdditive::psyIsZero);

	/**
	*	Context action of the {@code neg} operator.
	*/
	@SuppressWarnings("rawtypes")
	@OperatorType("neg")
	public static final ContextAction PSY_NEG
		=ContextAction.<PsyAdditive>ofFunction(PsyAdditive::psyNeg);

	/**
	*	Context action of the {@code nonzero} operator.
	*/
	@SuppressWarnings("rawtypes")
	@OperatorType("nonzero")
	public static final ContextAction PSY_NONZERO
		=ContextAction.<PsyAdditive>ofFunction(PsyAdditive::psyNonZero);

	/**
	*	Context action of the {@code sub} operator.
	*/
	@SuppressWarnings({"rawtypes", "unchecked"})
	@OperatorType("sub")
	public static final ContextAction PSY_SUB
		=ContextAction.<PsyAdditive, PsyAdditive>ofBiFunction(PsyAdditive::psySub);

	/**
	*	{@return the {@code additive} result of arithmetic negation of this object}
	*/
	public T psyNeg();

	/**
	*	{@return the {@code additive} result of arithmetic addition of specified object to this
	*	object}
	*
	*	@param oAdditive the specified object.
	*/
	public T psyAdd(final T oAdditive);

	/**
	*	{@return the {@code additive} result of arithmetic subtraction of specified object from this
	*	object}
	*
	*	@param oAdditive the specified object.
	*/
	public T psySub(final T oAdditive);

	/**
	*   {@return a {@code boolean} indicating whether this object represents a zero value}
	*/
	public default PsyBoolean psyIsZero()
	{
		return PsyBoolean.of(isZero());
	}

	/**
	*   {@return a {@code boolean} indicating whether this object represents a non-zero value}
	*/
	public default PsyBoolean psyNonZero()
	{
		return psyIsZero().psyNot();
	}

	public boolean isZero();
}
