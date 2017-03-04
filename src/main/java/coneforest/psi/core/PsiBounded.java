package coneforest.psi.core;

/**
*	A representation of Ψ-{@code bounded}, a type of a container with bounded
*	capacity.
*/
@coneforest.psi.Type("bounded")
public interface PsiBounded
	extends
		PsiLengthy
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
	default public PsiInteger psiCapacity()
	{
		return PsiInteger.valueOf(capacity());
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
	default public PsiBoolean psiIsFull()
	{
		return PsiBoolean.valueOf(isFull());
	}
}
