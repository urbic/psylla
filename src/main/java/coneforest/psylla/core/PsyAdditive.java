package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of {@code additive}, a type of object that is an operand
*	of arithmetic operation. This interface declares methods for addition,
*	subtraction and negation.
*
*	@param <T> a type of the second operand at binary operation.
*/
@Type("additive")
public interface PsyAdditive<T extends PsyAdditive>
	extends PsyObject
{

	/**
	*	Returns an {@code additive} representing the result of arithmetic
	*	negation of this object.
	*
	*	@return a negation.
	*/
	public T psyNeg();

	/**
	*	Returns an {@code additive} representing the result of arithmetic
	*	addition of given object to this object.
	*
	*	@param oAdditive given object.
	*	@return a sum.
	*/
	public T psyAdd(final T oAdditive);

	/**
	*	Returns an {@code additive} representing the result of arithmetic
	*	subtraction of given object from this object.
	*
	*	@param oAdditive given object.
	*	@return a difference.
	*/
	public T psySub(final T oAdditive);

	/**
	*   Returns a {@code boolean} indicating whether this object represents a
	*   zero value.
	*
	*   @return {@link PsyBoolean#TRUE} if this object represents a zero value,
	*   and {@link PsyBoolean#FALSE} otherwise.
	*/
	public PsyBoolean psyIsZero();

	/**
	*   Returns a {@code boolean} indicating whether this object represents a
	*   non-zero value.
	*
	*   @return {@link PsyBoolean#TRUE} if this object represents a non-zero value,
	*   and {@link PsyBoolean#FALSE} otherwise.
	*/
	default public PsyBoolean psyNonZero()
	{
		return psyIsZero().psyNot();
	}

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity21<PsyAdditive, PsyAdditive>("add", PsyAdditive::psyAdd),
			new PsyOperator.Arity11<PsyAdditive>("iszero", PsyAdditive::psyIsZero),
			new PsyOperator.Arity11<PsyAdditive>("neg", PsyAdditive::psyNeg),
			new PsyOperator.Arity11<PsyAdditive>("nonzero", PsyAdditive::psyNonZero),
			new PsyOperator.Arity21<PsyAdditive, PsyAdditive>("sub", PsyAdditive::psySub),
		};
}
