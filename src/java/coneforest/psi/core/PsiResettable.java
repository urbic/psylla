package coneforest.psi.core;

/**
*	A representation of Ψ-{@code evaluable}, a type of objects that can be
*	reset in some sense.
*/
public interface PsiResettable
	extends PsiObject
{
	/**
	*	@return a string {@code "resettable"}.
	*/
	@Override
	default public String getTypeName()
	{
		return "resettable";
	}

	/**
	 *	Reset this object.
	 *
	 *	@throws PsiException when error occured during reset.
	 */
	public void psiReset()
		throws PsiException;
}
