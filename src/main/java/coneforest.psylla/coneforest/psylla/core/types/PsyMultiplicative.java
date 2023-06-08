package coneforest.psylla.core.types;

import coneforest.psylla.Type;
import coneforest.psylla.core.errors.PsyUndefinedResult;

/**
*	A representation of {@code multiplicative}, a type of object that is an operand of
*	multiplicative operation. This interface declares methods for multiplication, division.
*
*	@param <T> a type of the second operand at binary operation.
*/
@Type("multiplicative")
public interface PsyMultiplicative<T extends PsyMultiplicative>
	extends PsyObject
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
	*	@throws PsyUndefinedResult when the result of division is not defined.
	*/
	public T psyDiv(final T oArithmetic)
		throws PsyUndefinedResult;

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity21<PsyArithmetic, PsyArithmetic>
				("div", PsyArithmetic::psyDiv),
			new PsyOperator.Arity21<PsyArithmetic, PsyArithmetic>
				("mul", PsyArithmetic::psyMul),
		};
}
