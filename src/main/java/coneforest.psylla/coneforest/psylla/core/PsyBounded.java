package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of {@code bounded}, a type of a container with bounded capacity.
*/
@Type("bounded")
public interface PsyBounded
	extends PsyLengthy
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

	/**
	*	Context action of the {@code capacity} operator.
	*/
	@OperatorType("capacity")
	public static final ContextAction PSY_CAPACITY
		=ContextAction.<PsyBounded>ofFunction(PsyBounded::psyCapacity);

	/**
	*	Context action of the {@code isfull} operator.
	*/
	@OperatorType("isfull")
	public static final ContextAction PSY_ISFULL
		=ContextAction.<PsyBounded>ofFunction(PsyBounded::psyIsFull);
}
