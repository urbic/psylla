package coneforest.psi.core;

public class PsiType<T extends PsiObject>
	extends PsiNamespace
{
	public PsiType(final String name)
	{
		super(name);
	}

	public String name()
	{
		return prefix();
	}
}
