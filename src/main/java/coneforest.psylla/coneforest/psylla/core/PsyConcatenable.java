package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of {@code concatenable}, a type of object that is an operand of
*	concatenation.
*
*	@param <T> a type of the second operand at concatenation.
*/
@Type("concatenable")
public interface PsyConcatenable<T extends PsyConcatenable<T>>
	extends PsyObject
{
	/**
	*	Context action of the {@code concat} operator.
	*/
	@SuppressWarnings({"unchecked", "rawtypes"})
	@OperatorType("concat")
	public static final ContextAction PSY_CONCAT
		=ContextAction.<PsyConcatenable, PsyConcatenable>ofBiFunction(PsyConcatenable::psyConcat);

	/**
	*	{@return the {@code concatenable} result of concatenation of this object and specified
	*	object}
	*
	*	@param oConcatenable the specified object.
	*	@throws PsyErrorException when the error occured during concatenation.
	*/
	public T psyConcat(final T oConcatenable)
		throws PsyErrorException;
}
