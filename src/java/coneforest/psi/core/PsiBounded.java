package coneforest.psi.core;

/**
*	A representation of Ψ-{@code bounded}, a type of a container with bounded
*	capacity.
*/
public interface PsiBounded
	extends
		PsiLengthy
{
	/**
	 *	@return a string {@code "bounded"}.
	 */
	@Override
	default public String typeName()
	{
		return "bounded";
	}

	public int capacity();

	default public PsiInteger psiCapacity()
	{
		return PsiInteger.valueOf(capacity());
	}

	default public boolean isFull()
	{
		return length()==capacity();
	}

	default public PsiBoolean psiIsFull()
	{
		return PsiBoolean.valueOf(isFull());
	}
}
