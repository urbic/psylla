package coneforest.psi;

/**
*	A representation of Ψ-{@code queuelike}, an abstraction of a queue of
*	Ψ-{@code object}s.
*
*	@param <T> a type of contained objects.
*/
public interface PsiQueuelike<T extends PsiObject>
	extends
		PsiBounded,
		PsiClearable,
		PsiContainer<T>
{
	/**
	 *	@return a string {@code "queuelike"}.
	 */
	@Override
	default public String getTypeName()
	{
		return "queuelike";
	}

	public T psiDequeue()
		throws PsiException;

	public void psiEnqueue(final T o)
		throws PsiException;

	public T psiTake()
		throws PsiException;

	public void psiGive(final T o)
		throws PsiException;
}
