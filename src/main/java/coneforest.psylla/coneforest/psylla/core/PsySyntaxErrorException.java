package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	 The representation of {@code syntaxerror} error thrown when syntax error occured.
*/
@ErrorType("syntaxerror")
public class PsySyntaxErrorException
	extends PsyErrorException
{
	/**
	*	Constructs a new {@code exception} object with null as its detail message.
	*/
	public PsySyntaxErrorException()
	{
		super();
	}
}
