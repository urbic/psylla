package coneforest.psylla.core;

import coneforest.psylla.*;

/**
*	A representation of {@code logical}, a type of object that is an operand of
*	logical operation. This interface declares methods for logical negation,
*	disjunction, conjunction and exclusive disjunction.
*
*	@param <T> a type of the second operand at binary operation.
*/
@coneforest.psylla.Type("logical")
public interface PsyLogical<T extends PsyLogical>
	extends PsyObject
{

	/**
	*	Returns a result of logical negation of this object.
	*
	*	@return a result.
	*/
	public PsyLogical psyNot();

	/**
	*	Returns a result of logical disjunction of this object and given
	*	object.
	*
	*	@param oLogical given object.
	*	@return a result.
	*/
	public PsyLogical psyOr(final T oLogical);

	/**
	*	Returns a result of logical conjunction of this object and given
	*	object.
	*
	*	@param oLogical given object.
	*	@return a result.
	*/
	public PsyLogical psyAnd(final T oLogical);

	/**
	*	Returns a result of logical exclusive disjunction of this object and
	*	given object.
	*
	*	@param oLogical given object.
	*	@return a result.
	*/
	public PsyLogical psyXor(final T oLogical);

	/**
	*	Context action of the {@code and} operator.
	*/
	@OperatorType("and")
	public static final ContextAction PSY_AND
		=ContextAction.<PsyLogical, PsyLogical>ofBiFunction(PsyLogical::psyAnd);

	/**
	*	Context action of the {@code not} operator.
	*/
	@OperatorType("not")
	public static final ContextAction PSY_NOT
		=ContextAction.<PsyLogical>ofFunction(PsyLogical::psyNot);

	/**
	*	Context action of the {@code or} operator.
	*/
	@OperatorType("or")
	public static final ContextAction PSY_OR
		=ContextAction.<PsyLogical, PsyLogical>ofBiFunction(PsyLogical::psyOr);

	/**
	*	Context action of the {@code xor} operator.
	*/
	@OperatorType("xor")
	public static final ContextAction PSY_XOR
		=ContextAction.<PsyLogical, PsyLogical>ofBiFunction(PsyLogical::psyXor);
}
