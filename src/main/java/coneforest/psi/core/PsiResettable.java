package coneforest.psi.core;

/**
*	A representation of Ψ-{@code resettable}, a type of objects that can be
*	reset in some sense.
*/
@coneforest.psi.Type("resettable")
public interface PsiResettable
	extends PsiObject
{

	/**
	*	Reset this object.
	*
	*	@throws PsiException when error occured during reset.
	*/
	public void psiReset()
		throws PsiException;
}
