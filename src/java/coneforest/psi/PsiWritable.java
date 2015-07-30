package coneforest.psi;

public interface PsiWritable
	extends PsiObject
{
	@Override
	default public String getTypeName()
	{
		return "writable";
	}

	public void psiWrite(PsiInteger character)
		throws PsiException;

	public void psiWriteString(PsiStringy stringy)
		throws PsiException;
}
