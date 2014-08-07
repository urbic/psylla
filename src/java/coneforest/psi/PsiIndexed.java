package coneforest.psi;

public interface PsiIndexed<K, V extends PsiObject>
{
	public V psiGet(K key)
		throws PsiException;

	public void psiPut(K key, V value)
		throws PsiException;
}