package coneforest.psi;

/**
 *	A type of dictionary-like objects.
 *
 *	@param <V> a type of contained values.
 */
public interface PsiDictionarylike<V extends PsiObject>
	extends
		PsiLengthy,
		PsiIndexed<PsiStringlike, V>,
		PsiIterable<java.util.Map.Entry<String, V>>,
		PsiClearable
{
	public V get(String key)
		throws PsiException;

	public void put(String key, V value);

	public boolean known(String keyString);

	public PsiBoolean psiKnown(PsiStringlike key);

	public void undef(String keyString);

	/**
	 *	Delete a key and a value associated with it from this object.
	 *
	 *	@param key a key. 
	 */
	public void psiUndef(PsiStringlike key);

	public PsiSet psiKeys();

	public PsiArray psiValues();

	@Override
	public PsiDictionarylike<V> psiSlice(PsiIterable<PsiStringlike> keys)
		throws PsiException;
}
