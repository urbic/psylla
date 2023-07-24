package coneforest.psylla.core;

import coneforest.psylla.*;
import java.io.StringReader;

/**
*	An implementation of {@code stringreader}, a character stream whose source is a {@code textual}.
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
		super(new StringReader(string));
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

	/**
	*	Context action of the {@code stringreader} operator.
	*/
	@OperatorType("stringreader")
	public static final ContextAction PSY_STRINGREADER
		=ContextAction.<PsyTextual>ofFunction(PsyStringReader::new);
}
