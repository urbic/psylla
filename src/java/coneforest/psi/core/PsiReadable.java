package coneforest.psi.core;

/**
*	A representation of Ψ-{@code readable}, a type of objects that can be
*	treated as source of characters.
*/
public interface PsiReadable
	extends PsiObject
{
	/**
	*	@return a string {@code "readable"}.
	*/
	@Override
	default public String getTypeName()
	{
		return "readable";
	}

	public int read()
		throws PsiException;

	/**
	*	Read a character (Ψ-{@code integer}) from this object and returns it.
	*	Returns {@link PsiInteger#MINUS_ONE} when end of input has been
	*	reached.
	*
	*	@return a Ψ-{@code integer} representing the character read from this
	*	object.
	*	@throws PsiException when error occurs.
	*/
	default public PsiInteger psiRead()
		throws PsiException
	{
		return PsiInteger.valueOf(read());
	}

	/**
	 *	Read a Ψ-{@code string} from this object and returns it.
	 *
	 *	@param count a Ψ-{@code integer} representing the length of the string.
	 *	@return a string read.
	 *	@throws PsiException when error occurs.
	 */
	public PsiString psiReadString(PsiInteger count)
		throws PsiException;

	/**
	 *	Read a line (Ψ-{@code string}) from this object and returns it.
	 *
	 *	@return a line read,
	 *	@throws PsiException when error occurs.
	 */
	public PsiString psiReadLine(PsiStringy stringy)
		throws PsiException;

	/**
	 *	Skips characters. This method will block until some characters are
	 *	available, an I/O error occurs, or end of input is reached.
	 *
	 *	@param count a Ψ-{@code integer} representing the number of characters
	 *	to be skipped.
	 *	@return a Ψ-{@code boolean} indicating whether all the characters were
	 *	skipped successfully
	 *	@throws PsiException when an error occurs.
	 */
	public PsiBoolean psiSkip(PsiInteger count)
		throws PsiException;

	public PsiBoolean psiReady()
		throws PsiException;
}
