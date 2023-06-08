package coneforest.psylla.core.types;

import coneforest.psylla.Type;

/**
*	An implementation of {@code stringreader}, a character stream whose source
*	is a {@code textual}.
*/
@Type("stringreader")
public class PsyStringReader
	extends PsyReader
{
	/**
	*	Creates a new {@code stringreader} object.
	*
	*	@param string a string providing the character string.
	*/
	public PsyStringReader(final String string)
	{
		super(new java.io.StringReader(string));
	}

	/**
	*	Creates a new {@code stringreader} object.
	*
	*	@param oTextual a {@code textual} providing the character string.
	*/
	public PsyStringReader(final PsyTextual oTextual)
	{
		this(oTextual.stringValue());
	}

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity11<PsyTextual>
				("stringreader", PsyStringReader::new),
		};
}
