package coneforest.psi;

public interface PsiDictionarylike<V extends PsiObject>
	extends
		PsiLengthy,
		PsiIndexed<PsiStringlike, V>,
		PsiIterable<java.util.Map.Entry<String, V>>,
		PsiClearable
{
	public V psiGet(String key)
		throws PsiException;

	public void psiPut(String key, V value);

	public PsiBoolean psiKnown(String keyString);
	
	public PsiBoolean psiKnown(PsiStringlike key);

	public void psiUndef(String keyString);
	
	public void psiUndef(PsiStringlike key);

	public PsiSet psiKeys();
}
