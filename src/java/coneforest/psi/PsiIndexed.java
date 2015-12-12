package coneforest.psi;

/**
*	A representation of Ψ-{@code indexed}, a type of the container whose
*	elements are indexed.
*
*	@param <K> a type of indices.
*	@param <V> a type of elements.
*/
public interface PsiIndexed<K extends PsiObject, V extends PsiObject>
	extends PsiObject
{
	/**
	*	@return a string {@code "indexed"}.
	*/
	@Override
	default public String getTypeName()
	{
		return "indexed";
	}

	/**
	*	Returns a Ψ boolean value indicating if given key or index exists
	*	in this object.
	*
	*	@param key a key or an index.
	*	@return a result.
	*/
	public PsiBoolean psiKnown(K key);

	/**
	*	Returns the element with given key or index.
	*
	*	@param key a key or an index.
	*	@return an element.
	*	@throws PsiException when index is out of range.
	*/
	public V psiGet(K key)
		throws PsiException;

	/**
	*	Stores an element with given key or index. In {@link PsiArraylike}
	*	containers replaces existing element. In {@link PsiDictlike}
	*	containers replaces an old or creates a new element associated with
	*	specified key.
	*
	*	@param key a key or an index.
	*	@param value an element to be stored.
	*	@throws PsiException when key is absent or index is out of range.
	*/
	public void psiPut(K key, V value)
		throws PsiException;

	/**
	*	Deletes a key or index and a value associated with it from this object.
	*
	*	@param key a key or an index. 
	*	@throws PsiException when key is absent or index is out of range.
	*/
	public void psiDelete(K key)
		throws PsiException;

	public V psiExtract(K key)
		throws PsiException;

	/**
	*	Returns a container of the same type as this object consisting of keys
	*	or indices from given Ψ-{@code iterable} and of associated values.
	*
	*	@param keys a sequence of keys. 
	*	@return a container.
	*	@throws PsiException when key is absent or index is out of range.
	*/
	public PsiIndexed<K, V> psiSlice(PsiIterable<K> keys)
		throws PsiException;

	public PsiArraylike<V> psiGetAll(PsiIterable<K> keys)
		throws PsiException;

	/**
	*	Returns an enumeration (Ψ-{@code iterable}) of all the keys of this
	*	object.
	*
	*	@return an enumeration of keys.
	*/
	public PsiIterable<K> psiKeys();

	/**
	*	Returns an enumeration (Ψ-{@code iterable}) of all the values of this
	*	object.
	*
	*	@return an enumeration of values.
	*/
	public PsiIterable<V> psiValues();

	/**
	*	Returns an enumeration (Ψ-{@code iterable}) of all the keys and values
	*	of this object.
	*
	*	@return an enumeration of entries.
	*/
	public PsiIterable<PsiObject> psiEntries();
}
