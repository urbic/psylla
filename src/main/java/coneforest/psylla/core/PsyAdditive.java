package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of Ψ-{@code additive}, a type of object that is an operand
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
	*	Returns a Ψ-{@code additive} representing the result of arithmetic
	*	negation of this object.
	*
	*	@return a negation.
	*/
	public T psyNeg();

	/**
	*	Returns a Ψ-{@code additive} representing the result of arithmetic
	*	addition of given object to this object.
	*
	*	@param oAdditive given object.
	*	@return a sum.
	*/
	public T psyAdd(final T oAdditive);
	//public <U extends T> T psyAdd(final U oAdditive);

	/**
	*	Returns a Ψ-{@code additive} representing the result of arithmetic
	*	subtraction of given object from this object.
	*
	*	@param oAdditive given object.
	*	@return a difference.
	*/
	public T psySub(final T oAdditive);


	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity21<PsyAdditive, PsyAdditive>("add", PsyAdditive::psyAdd),
			new PsyOperator.Arity11<PsyAdditive>("neg", PsyAdditive::psyNeg),
			new PsyOperator.Arity21<PsyAdditive, PsyAdditive>("sub", PsyAdditive::psySub),
		};

}
