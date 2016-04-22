package coneforest.psi.core;

/**
*	A representation of Ψ-{@code flushable}, a type of objects that can be
*	flushed in some sense.
*/
public interface PsiFlushable
	extends PsiObject
{
	/**
	*	@return a string {@code "flushable"}.
	*/
	@Override
	default public String getTypeName()
	{
		return "flushable";
	}

	/**
	*	Flush this object.
	*
	*	@throws PsiException when error occurs during flush.
	*/
	public void psiFlush()
		throws PsiException;
}
