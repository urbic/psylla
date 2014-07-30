package coneforest.psi;

public interface PsiHashlike<V extends PsiObject>
	extends PsiComposite<V>, PsiIndexed<PsiStringlike, V>, PsiIterable<java.util.Map.Entry<String, V>>
{
	public void psiUndef(PsiStringlike key);

	public PsiBoolean psiKnown(PsiStringlike key);

	public java.util.Iterator<java.util.Map.Entry<String, V>> iterator();
}
