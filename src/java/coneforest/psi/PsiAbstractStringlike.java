package coneforest.psi;

abstract public class PsiAbstractStringlike
	extends PsiObject
	implements
		PsiComposite<PsiInteger>,
		PsiStringlike
{
	@Override
	public PsiInteger psiLength()
	{
		return new PsiInteger(getString().length());
	}

	@Override
	public PsiBoolean psiIsEmpty()
	{
		return new PsiBoolean(getString().isEmpty());
	}
}
