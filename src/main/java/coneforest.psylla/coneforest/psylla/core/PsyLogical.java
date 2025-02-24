package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of {@code logical}, a type of object that is an operand of logical operation.
*	This interface declares methods for logical negation, disjunction, conjunction and exclusive
*	disjunction.
*
*	@param <T> a type of the second operand at binary operation.
*/
@Type("logical")
public interface PsyLogical<T extends PsyLogical<T>>
	extends PsyObject
{

	/**
	*	Context action of the {@code and} operator.
	*/
	@SuppressWarnings({"unchecked", "rawtypes"})
	@OperatorType("and")
	public static final ContextAction PSY_AND
		=ContextAction.<PsyLogical, PsyLogical>ofBiFunction(PsyLogical::psyAnd);

	/**
	*	Context action of the {@code not} operator.
	*/
	@SuppressWarnings("rawtypes")
	@OperatorType("not")
	public static final ContextAction PSY_NOT
		=ContextAction.<PsyLogical>ofFunction(PsyLogical::psyNot);

	/**
	*	Context action of the {@code or} operator.
	*/
	@SuppressWarnings({"unchecked", "rawtypes"})
	@OperatorType("or")
	public static final ContextAction PSY_OR
		=ContextAction.<PsyLogical, PsyLogical>ofBiFunction(PsyLogical::psyOr);

	/**
	*	Context action of the {@code xor} operator.
	*/
	@SuppressWarnings({"unchecked", "rawtypes"})
	@OperatorType("xor")
	public static final ContextAction PSY_XOR
		=ContextAction.<PsyLogical, PsyLogical>ofBiFunction(PsyLogical::psyXor);

	/**
	*	{@return a result of logical negation of this object}
	*/
	public PsyLogical<T> psyNot();

	/**
	*	{@return a result of logical disjunction of this object and given object}
	*
	*	@param oLogical given object.
	*/
	public PsyLogical<T> psyOr(final T oLogical);

	/**
	*	{@return a result of logical conjunction of this object and given object}
	*
	*	@param oLogical given object.
	*/
	public PsyLogical<T> psyAnd(final T oLogical);

	/**
	*	{@return a result of logical exclusive disjunction of this object and given object}
	*
	*	@param oLogical given object.
	*/
	public PsyLogical<T> psyXor(final T oLogical);
}
