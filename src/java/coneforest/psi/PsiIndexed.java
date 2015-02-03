package coneforest.psi;

/**
*	A type of container whose elements are indexed.
*	@param K A type of indices.
*	@param V A type of elements.
*/
public interface PsiIndexed<K, V extends PsiObject>
	extends PsiObject
{
	/**
	*	Returns the element with given key or index.
	*	@param key A key or an index.
	*	@return An element.
	*	@throws PsiException when index is out of range.
	*/
	public V psiGet(K key)
		throws PsiException;

	/**
	*	Store an element with given key or index. In {@link PsiArraylike}
	*	containers replaces existing element. In {@link PsiDictionarylike}
	*	containers replaces an old or creates a new element associated with
	*	specified key.
	*	@param key A key or an index.
	*	@param value An element to be stored.
	*	@throws PsiException when index is out of range.
	*/
	public void psiPut(K key, V value)
		throws PsiException;

	public V psiDelete(K key)
		throws PsiException;

	public PsiIndexed<K, V> psiSlice(PsiIterable<K> keys)
		throws PsiException;
}
