package coneforest.psylla.core;

import coneforest.psylla.*;

/**
*	The representation of {@code additive}, a type of object that is an operand of arithmetic
*	operation. This interface declares methods for addition, subtraction and negation.
*
*	@param <T> a type of the second operand at binary operation.
*/
@Type("additive")
public interface PsyAdditive<T extends PsyAdditive>
	extends PsyObject
{

	/**
	*	Returns the {@code additive} result of arithmetic negation of this object.
	*
	*	@return the {@code additive} result of arithmetic negation.
	*/
	public T psyNeg();

	/**
	*	Returns the {@code additive} result of arithmetic addition of specified object to this
	*	object.
	*
	*	@param oAdditive the specified object.
	*	@return the sum.
	*/
	public T psyAdd(final T oAdditive);

	/**
	*	Returns the {@code additive} result of arithmetic subtraction of specified object from this
	*	object.
	*
	*	@param oAdditive the specified object.
	*	@return a difference.
	*/
	public T psySub(final T oAdditive);

	/**
	*   Returns a {@code boolean} indicating whether this object represents a zero value.
	*
	*   @return a {@code boolean} indicating whether this object represents a zero value.
	*/
	public PsyBoolean psyIsZero();

	/**
	*   Returns a {@code boolean} indicating whether this object represents a non-zero value.
	*
	*   @return a {@code boolean} indicating whether this object represents a non-zero value.
	*/
	default public PsyBoolean psyNonZero()
	{
		return psyIsZero().psyNot();
	}

	/**
	*	Context action of the {@code add} operator.
	*/
	@OperatorType("add")
	public static final ContextAction PSY_ADD
		=ContextAction.<PsyAdditive, PsyAdditive>ofBiFunction(PsyAdditive::psyAdd);

	/**
	*	Context action of the {@code iszero} operator.
	*/
	@OperatorType("iszero")
	public static final ContextAction PSY_ISZERO
		=ContextAction.<PsyAdditive>ofFunction(PsyAdditive::psyIsZero);

	/**
	*	Context action of the {@code neg} operator.
	*/
	@OperatorType("neg")
	public static final ContextAction PSY_NEG
		=ContextAction.<PsyAdditive>ofFunction(PsyAdditive::psyNeg);

	/**
	*	Context action of the {@code nonzero} operator.
	*/
	@OperatorType("nonzero")
	public static final ContextAction PSY_NONZERO
		=ContextAction.<PsyAdditive>ofFunction(PsyAdditive::psyNonZero);

	/**
	*	Context action of the {@code sub} operator.
	*/
	@OperatorType("sub")
	public static final ContextAction PSY_SUB
		=ContextAction.<PsyAdditive, PsyAdditive>ofBiFunction(PsyAdditive::psySub);
}
