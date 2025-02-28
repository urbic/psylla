package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of {@code lengthy}, an object that has length in some sense. Usually the
*	length is the number of elements in the container.
*/
@Type("lengthy")
public interface PsyLengthy
	extends PsyObject
{
	/**
	*	Context action of the {@code isempty} operator.
	*/
	@OperatorType("isempty")
	public static final ContextAction PSY_ISEMPTY
		=ContextAction.<PsyLengthy>ofFunction(PsyLengthy::psyIsEmpty);

	/**
	*	Context action of the {@code length} operator.
	*/
	@OperatorType("length")
	public static final ContextAction PSY_LENGTH
		=ContextAction.<PsyLengthy>ofFunction(PsyLengthy::psyLength);

	/**
	*	{@return a length of this object}
	*/
	public int length();

	/**
	*	{@return a boolean indicating if this container is empty (has zero length)}
	*/
	public default boolean isEmpty()
	{
		return length()==0;
	}

	/**
	*	{@return an {@code integer} representing the length of this object}
	*/
	public default PsyInteger psyLength()
	{
		return PsyInteger.of(length());
	}

	/**
	*	{@return a {@code boolean} indicating if this container is empty (has zero length)}
	*/
	public default PsyBoolean psyIsEmpty()
	{
		return PsyBoolean.of(isEmpty());
	}
}
