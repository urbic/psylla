package coneforest.psylla.core.types;

import coneforest.psylla.Type;

/**
*	A representation of {@code bounded}, a type of a container with bounded
*	capacity.
*/
@Type("bounded")
public interface PsyBounded
	extends
		PsyLengthy
{

	/**
	*	Returns the capacity of this container.
	*
	*	@return the capacity of this container.
	*/
	public int capacity();

	/**
	*	Returns the {@code integer} capacity of this container.
	*
	*	@return the {@code integer} capacity of this container.
	*/
	default public PsyInteger psyCapacity()
	{
		return PsyInteger.of(capacity());
	}

	/**
	*	Returns a boolean indicating whether this container is full.
	*
	*	@return {@code true} if this container is full, and {@code false}
	*	otherwise.
	*/
	default public boolean isFull()
	{
		return length()==capacity();
	}

	/**
	*	Returns a {@code boolean} indicating whether this container is full.
	*
	*	@return the {@code boolean} {@code true} if this container is full, and
	*	the {@code boolean} {@code false} otherwise.
	*/
	default public PsyBoolean psyIsFull()
	{
		return PsyBoolean.of(isFull());
	}

}
