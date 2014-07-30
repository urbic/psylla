package coneforest.psi;

public interface PsiWritable
{
	public void psiWrite(PsiInteger character)
		throws PsiException;

	public void psiWriteString(PsiString string)
		throws PsiException;
}
