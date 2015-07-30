package coneforest.psi;

/**
 *	A type of Ψ objects that can be iterated.
 *	@param <T> the type of elements returned by the iterator.
 */
public interface PsiIterable<T>
	extends
		PsiObject,
		Iterable<T>
{
	@Override
	default public String getTypeName()
	{
		return "iterable";
	}

	/**
	 *	Returns an iterator over elements of type T.
	 *	@return an iterator.
	 */
	@Override
	public java.util.Iterator<T> iterator();
}
