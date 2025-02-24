package coneforest.psylla.core;

import coneforest.psylla.runtime.*;
import java.io.StringReader;

/**
*	An implementation of {@code stringreader}, a character stream whose source is a {@code string}.
*/
@Type("stringreader")
public class PsyStringReader
	extends PsyReader
{
	/**
	*	Context action of the {@code stringreader} operator.
	*/
	@OperatorType("stringreader")
	public static final ContextAction PSY_STRINGREADER
		=ContextAction.<PsyString>ofFunction(PsyStringReader::new);

	/**
	*	Creates a new {@code stringreader} object.
	*
	*	@param string a string to read from.
	*/
	public PsyStringReader(final String string)
	{
		super(new StringReader(string));
	}

	/**
	*	Creates a new {@code stringreader} object.
	*
	*	@param oString a {@code string} to read from.
	*/
	public PsyStringReader(final PsyString oString)
	{
		this(oString.stringValue());
	}
}
