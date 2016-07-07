package coneforest.psi.core;

/**
*	A representation of Ψ-{@code writable}, a type of objects that can be
*	treated as sink for characters.
*/
public interface PsiWritable
	extends PsiObject
{
	/**
	*	@return a string {@code "writable"}.
	*/
	@Override
	default public String typeName()
	{
		return "writable";
	}

	/**
	*	Writes a Ψ-{@code integer} character to this object.
	*
	*	@param oCharacter a Ψ-{@code integer} object representing a character
	*	to be written.
	*	@throws PsiException when an error occurs during write.
	*/
	public void psiWrite(final PsiInteger oCharacter)
		throws PsiException;

	/**
	*	Writes a Ψ-{@code stringy} string of characters to this object.
	*
	*	@param oStringy a Ψ-{@code stringy} object representing a string to be
	*	written.
	*	@throws PsiException when an error occurs during write.
	*/
	public void psiWriteString(final PsiStringy oStringy)
		throws PsiException;

}
