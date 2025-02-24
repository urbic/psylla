package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

@SuppressWarnings("serial")
@ErrorType("invalidregexp")
public class PsyInvalidRegExpException
	extends PsySyntaxErrorException
{
	/**
	*	Constructs a new {@code exception} object with null as its detail message.
	*/
	public PsyInvalidRegExpException()
	{
	}

	/**
	*	Constructs a new {@code exception} object with the specified cause.
	*
	*	@param cause the cause.
	*/
	public PsyInvalidRegExpException(final Throwable cause)
	{
		super(cause);
	}
}
