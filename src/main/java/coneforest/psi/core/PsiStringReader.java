package coneforest.psi.core;

/**
*	An implementation of Ψ-{@code stringreader}, a character stream whose
*	source is a Ψ-{@code stringy}.
*/
@coneforest.psi.Type("stringreader")
public class PsiStringReader
	extends PsiReader
{
	/**
	*	Creates a new Ψ-{@code stringreader}.
	*
	*	@param string a string providing the character string.
	*/
	public PsiStringReader(final String string)
	{
		super(new java.io.StringReader(string));
	}

	/**
	*	Creates a new Ψ-{@code stringreader}.
	*
	*	@param oStringy a Ψ-{@code stringy} providing the character string.
	*/
	public PsiStringReader(final PsiStringy oStringy)
	{
		this(oStringy.stringValue());
	}
}
