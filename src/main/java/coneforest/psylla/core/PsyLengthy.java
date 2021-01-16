package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of Ψ-{@code lengthy}, a type of an object that has length
*	in some sense. Ususally the length is the number of elements in the
*	container.
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
	*	Returns a Ψ-{@code integer} representing the length of this object.
	*
	*	@return a Ψ-{@code integer} length.
	*/
	default public PsyInteger psyLength()
	{
		return PsyInteger.valueOf(length());
	}

	/**
	*	Returns a Ψ-{@code boolean} indicating if this container is empty (has
	*	zero length).
	*
	*	@return a Ψ-{@code boolean} result.
	*/
	default public PsyBoolean psyIsEmpty()
	{
		return PsyBoolean.valueOf(isEmpty());
	}

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity11<PsyLengthy>("isempty", PsyLengthy::psyIsEmpty),
			new PsyOperator.Arity11<PsyLengthy>("length", PsyLengthy::psyLength),
		};

}
