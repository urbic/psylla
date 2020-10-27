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
	@Operator(name="add", type=PsyOperator.Arity21.class)
	//@Operator(name="add", type=Operator.ARITY_21)
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

	//public static final PsyType<PsyAdditive> TYPE
	//	=new PsyType<PsyAdditive>("additive");
}
