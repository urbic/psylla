package coneforest.psylla.core;

import coneforest.psylla.*;

/**
*	The representation of {@code lengthy}, an object that has length in some sense. Usually the
*	length is the number of elements in the container.
*/
@Type("lengthy")
public interface PsyLengthy
	extends PsyObject
{

	/**
	*	Returns a length of this object.
	*
	*	@return a length.
	*/
	public int length();

	/**
	*	Returns a boolean indicating if this container is empty (has zero length).
	*
	*	@return {@code true}, if this container is empty, and {@code false} otherwise.
	*/
	default public boolean isEmpty()
	{
		return length()==0;
	}

	/**
	*	Returns an {@code integer} representing the length of this object.
	*
	*	@return an {@code integer} length.
	*/
	default public PsyInteger psyLength()
	{
		return PsyInteger.of(length());
	}

	/**
	*	Returns a {@code boolean} indicating if this container is empty (has zero length).
	*
	*	@return a {@code boolean} result.
	*/
	default public PsyBoolean psyIsEmpty()
	{
		return PsyBoolean.of(isEmpty());
	}

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
}
