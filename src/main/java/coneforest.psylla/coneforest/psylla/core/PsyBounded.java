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

	/**
	*	{@return the capacity of this container}
	*/
	public int capacity();

	/**
	*	{@returns the {@code integer} capacity of this container}
	*/
	public default PsyInteger psyCapacity()
	{
		return PsyInteger.of(capacity());
	}

	/**
	*	{@return a boolean indicating whether this container is full}
	*/
	public default boolean isFull()
	{
		return length()==capacity();
	}

	/**
	*	{@return a {@code boolean} indicating whether this container is full}
	*/
	public default PsyBoolean psyIsFull()
	{
		return PsyBoolean.of(isFull());
	}
}
