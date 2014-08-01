package coneforest.psi;

public interface PsiDictionarylike<V extends PsiObject>
	extends
		PsiComposite<V>,
		PsiIndexed<PsiStringlike, V>,
		PsiIterable<java.util.Map.Entry<String, V>>
{
	public V psiGet(String key)
		throws PsiException;

	public void psiPut(String key, V value);

	public PsiBoolean psiKnown(String keyString);
	
	public PsiBoolean psiKnown(PsiStringlike key);

	public void psiUndef(String keyString);
	
	public void psiUndef(PsiStringlike key);

	public java.util.Iterator<java.util.Map.Entry<String, V>> iterator();
}
