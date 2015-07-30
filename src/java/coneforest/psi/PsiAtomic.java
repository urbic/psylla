package coneforest.psi;

public interface PsiAtomic
	extends PsiObject
{
	@Override
	default public String getTypeName()
	{
		return "atomic";
	}
}
