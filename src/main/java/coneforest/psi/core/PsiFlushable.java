package coneforest.psi.core;

/**
*	A representation of Ψ-{@code flushable}, a type of objects that can be
*	flushed in some sense.
*/
@coneforest.psi.Type("flushable")
public interface PsiFlushable
	extends PsiObject
{

	/**
	*	Flush this object.
	*
	*	@throws PsiException when error occurs during flush.
	*/
	public void psiFlush()
		throws PsiException;
}
