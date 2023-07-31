package coneforest.psylla.core;

import coneforest.psylla.*;

/**
*	The representation of {@code multiplicative}, a type of object that is an operand of
*	multiplicative operation. This interface declares methods for multiplication, division.
*
*	@param <T> a type of the second operand at binary operation.
*/
@Type("multiplicative")
public interface PsyMultiplicative<T extends PsyMultiplicative>
	extends PsyObject
{
	public T psyReciprocal()
		throws PsyUndefinedResultException;

	/**
	*	Returns a result of arithmetic multiplication of given object by this object.
	*
	*	@param oMultiplicative a given object.
	*	@return a product.
	*/
	public T psyMul(final T oMultiplicative);

	/**
	*	Returns a result of arithmetic division of this object by given object.
	*
	*	@param oMultiplicative a given object.
	*	@return a fraction.
	*	@throws PsyUndefinedResultException when the result of division is not defined.
	*/
	public T psyDiv(final T oMultiplicative)
		throws PsyUndefinedResultException;

	/**
	*	Context action of the {@code div} operator.
	*/
	@OperatorType("div")
	public static final ContextAction PSY_DIV
		=ContextAction.<PsyMultiplicative, PsyMultiplicative>ofBiFunction(PsyMultiplicative::psyDiv);

	/**
	*	Context action of the {@code mul} operator.
	*/
	@OperatorType("mul")
	public static final ContextAction PSY_MUL
		=ContextAction.<PsyMultiplicative, PsyMultiplicative>ofBiFunction(PsyMultiplicative::psyMul);

	/**
	*	Context action of the {@code reciprocal} operator.
	*/
	@OperatorType("reciprocal")
	public static final ContextAction PSY_RECIPROCAL
		=ContextAction.<PsyMultiplicative>ofFunction(PsyMultiplicative::psyReciprocal);

}
