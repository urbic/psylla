package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of Ψ-{@code bounded}, a type of a container with bounded
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
	*	Returns the Ψ-{@code integer} capacity of this container.
	*
	*	@return the Ψ-{@code integer} capacity of this container.
	*/
	default public PsyInteger psyCapacity()
	{
		return PsyInteger.valueOf(capacity());
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
	*	Returns a Ψ-{@code boolean} indicating whether this container is full.
	*
	*	@return the Ψ-{@code boolean} {@code true} if this container is full,
	*	and the Ψ-{@code boolean} {@code false} otherwise.
	*/
	default public PsyBoolean psyIsFull()
	{
		return PsyBoolean.valueOf(isFull());
	}

}
