package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of Ψ-{@code arithmetic}, a type of object that is an
*	operand of arithmetic operation. This interface declares methods for
*	multiplication, division.
*
*	@param <T> a type of the second operand at binary operation.
*/
@Type("arithmetic")
public interface PsyArithmetic<T extends PsyArithmetic>
	extends
		PsyAdditive<T>
{

	/**
	*	Returns a result of arithmetic multiplication of given object by this object.
	*
	*	@param oArithmetic a given object.
	*	@return a product.
	*/
	public T psyMul(final T oArithmetic);

	/**
	*	Returns a result of arithmetic division of this object by given object.
	*
	*	@param oArithmetic a given object.
	*	@return a fraction.
	*/
	public T psyDiv(final T oArithmetic);

	/**
	*	Returns a Ψ-{@code boolean} indicating whether this object represents a
	*	zero value.
	*
	*	@return {@link PsyBoolean#TRUE} if this object represents a zero value,
	*	and {@link PsyBoolean#FALSE} otherwise.
	*/
	public PsyBoolean psyIsZero();

	/**
	*	Returns a Ψ-{@code boolean} indicating whether this object represents a
	*	non-zero value.
	*
	*	@return {@link PsyBoolean#TRUE} if this object represents a non-zero value,
	*	and {@link PsyBoolean#FALSE} otherwise.
	*/
	default public PsyBoolean psyNotZero()
	{
		return psyIsZero().psyNot();
	}

}
