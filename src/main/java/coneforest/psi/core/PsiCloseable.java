package coneforest.psi.core;

/**
*	A representation of Ψ-{@code closeable}, a type of objects that can be
*	closed in some sense.
*/
@coneforest.psi.Type("closeable")
public interface PsiCloseable
	extends PsiObject
{

	/**
	*	Close this object.
	*
	*	@throws PsiException when error occured during closing.
	*/
	public void psiClose()
		throws PsiException;
}
