package coneforest.psi;

public interface PsiHashlike<V extends PsiObject>
	extends PsiComposite<V>, PsiIndexed<PsiAbstractStringlike, V>, PsiIterable<java.util.Map.Entry<String, V>>
{
	public void psiUndef(PsiAbstractStringlike key);

	public PsiBoolean psiKnown(PsiAbstractStringlike key);

	public java.util.Iterator<java.util.Map.Entry<String, V>> iterator();
}
