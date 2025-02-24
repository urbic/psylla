package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	 The representation of {@code syntaxerror} error thrown when syntax error occured.
*/
@SuppressWarnings("serial")
@ErrorType("syntaxerror")
public class PsySyntaxErrorException
	extends PsyErrorException
{
	/**
	*	Constructs a new {@code exception} object with null as its detail message.
	*/
	public PsySyntaxErrorException()
	{
	}

	/**
	*	Constructs a new {@code exception} object with the specified cause.
	*
	*	@param cause the cause.
	*/
	public PsySyntaxErrorException(final Throwable cause)
	{
		super(cause);
	}
}
