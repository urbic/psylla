package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	An implementation of Ψ-{@code stringreader}, a character stream whose
*	source is a Ψ-{@code textual}.
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
	*	@param oTextual a Ψ-{@code textual} providing the character string.
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
