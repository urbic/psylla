package coneforest.psi;

/**
*	A representation of Ψ-{@code lengthy}, a type of object that has length in
*	some sense. Ususally the length is the number of elements in the container.
*/
public interface PsiLengthy
	extends PsiObject
{
	/**
	*	@return a string {@code "lengthy"}.
	*/
	default public String getTypeName()
	{
		return "lengthy";
	}

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
	default public PsiInteger psiLength()
	{
		return PsiInteger.valueOf(length());
	}

	/**
	*	Returns a Ψ-{@code boolean} indicating if this container is empty (has
	*	zero length).
	*
	*	@return a Ψ-{@code boolean} result.
	*/
	default public PsiBoolean psiIsEmpty()
	{
		return PsiBoolean.valueOf(isEmpty());
	}
}
