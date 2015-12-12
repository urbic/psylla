package coneforest.psi;

/**
*	A representation of Ψ-{@code clearable}, a type of objects that can be
*	cleared (emptied) in some sense.
*/
public interface PsiClearable
	extends PsiObject
{
	/**
	*	@return a string {@code "clearable"}.
	*/
	@Override
	default public String getTypeName()
	{
		return "clearable";
	}

	/**
	 *	Clear this object.
	 */
	public void psiClear();
}
