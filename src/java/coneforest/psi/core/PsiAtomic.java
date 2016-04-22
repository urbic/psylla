package coneforest.psi.core;
/**
 *	A representation of Ψ-{@code atomic}, a type of immutable objects.
 */
public interface PsiAtomic
	extends PsiObject
{
	@Override
	default public String getTypeName()
	{
		return "atomic";
	}
}
