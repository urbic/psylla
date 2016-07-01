package coneforest.psi.core;

/**
*	A representation of Ψ-{@code queuelike}, an abstraction of a queue of
*	Ψ-{@code object}s.
*
*	@param <T> a type of contained objects.
*/
public interface PsiQueuelike<T extends PsiObject>
	extends
		PsiBounded,
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

	/**
	 *	Removes and returns the head of this queue.
	 *	
	 *	@return the head of this queue.
	 *	@throws PsiException when this queue is empty.
	 */
	public T psiDequeue()
		throws PsiException;

	/**
	 *	Inserts an element into this queue.
	 *	
	 *	@param o the element to enqueue.
	 *	@throws PsiException when the element can not be inserted without
	 *	violation of the capacity restrictions.
	 */
	public void psiEnqueue(final T o)
		throws PsiException;

	public T psiTake()
		throws PsiException;

	public void psiGive(final T o)
		throws PsiException;
}
