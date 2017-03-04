package coneforest.psi.core;

/**
*	A representation of Ψ-{@code clearable}, a type of objects that can be
*	cleared (emptied) in some sense.
*/
@coneforest.psi.Type("clearable")
public interface PsiClearable
	extends PsiObject
{

	/**
	*	Clear this object.
	*/
	public void psiClear();
}
