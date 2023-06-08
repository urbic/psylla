package coneforest.psylla.core.types;

import coneforest.psylla.Type;

/**
*	A representation of {@code lengthy}, a type of an object that has length in
*	some sense. Usually the length is the number of elements in the container.
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
	*	Returns a boolean indicating if this container is empty (has zero
	*	length).
	*
	*	@return {@code true}, if this container is empty, and {@code false}
	*	otherwise.
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
	*	Returns a {@code boolean} indicating if this container is empty (has
	*	zero length).
	*
	*	@return a {@code boolean} result.
	*/
	default public PsyBoolean psyIsEmpty()
	{
		return PsyBoolean.of(isEmpty());
	}

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity11<PsyLengthy>
				("isempty", PsyLengthy::psyIsEmpty),
			new PsyOperator.Arity11<PsyLengthy>
				("length", PsyLengthy::psyLength),
		};

}
