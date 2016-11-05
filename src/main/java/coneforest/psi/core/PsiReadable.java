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
	default public String typeName()
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
	 *	@param oCount a Ψ-{@code integer} representing the length of the string.
	 *	@return a string read.
	 *	@throws PsiException when error occurs.
	 */
	public PsiString psiReadString(final PsiInteger oCount)
		throws PsiException;

	/**
	 *	Read a line (Ψ-{@code string}) from this object and returns it.
	 *
	 *	@return a line read,
	 *	@throws PsiException when error occurs.
	 */
	public PsiString psiReadLine()
		throws PsiException;

	/**
	 *	Skips characters. This method will block until some characters are
	 *	available, an I/O error occurs, or end of input is reached.
	 *
	 *	@param oCount a Ψ-{@code integer} representing the number of characters
	 *	to be skipped.
	 *	@return a Ψ-{@code boolean} indicating whether all the characters were
	 *	skipped successfully
	 *	@throws PsiException when an error occurs.
	 */
	public PsiBoolean psiSkip(final PsiInteger oCount)
		throws PsiException;

	/**
	 *	Returns a Ψ-{@code boolean} indicating whether this object is ready to
	 *	be read.
	 *
	 *	@return {@code true} if this object is ready to be read, and {@code
	 *	false} otherwise.
	 *	@throws PsiException when I/O error occurs.
	 */
	public PsiBoolean psiReady()
		throws PsiException;
}
