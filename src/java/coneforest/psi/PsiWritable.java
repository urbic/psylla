package coneforest.psi;

public interface PsiWritable
	extends PsiObject
{
	public void psiWrite(PsiInteger character)
		throws PsiException;

	public void psiWriteString(PsiStringlike stringlike)
		throws PsiException;
}
