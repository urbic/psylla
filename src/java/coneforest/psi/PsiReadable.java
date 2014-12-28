package coneforest.psi;

public interface PsiReadable
	extends PsiObject
{
	public PsiInteger psiRead()
		throws PsiException;

	public PsiString psiReadString(PsiInteger count)
		throws PsiException;

	public PsiString psiReadLine(PsiStringlike stringlike)
		throws PsiException;
}
