package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	An implementation of Ψ-{@code stringreader}, a character stream whose
*	source is a Ψ-{@code stringy}.
*/
@Type("stringreader")
public class PsyStringReader
	extends PsyReader
{
	/**
	*	Creates a new Ψ-{@code stringreader}.
	*
	*	@param string a string providing the character string.
	*/
	public PsyStringReader(final String string)
	{
		super(new java.io.StringReader(string));
	}

	/**
	*	Creates a new Ψ-{@code stringreader}.
	*
	*	@param oStringy a Ψ-{@code stringy} providing the character string.
	*/
	public PsyStringReader(final PsyStringy oStringy)
	{
		this(oStringy.stringValue());
	}

}
