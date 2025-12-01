package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of the {@code unsupported} error thrown by the operator attempting to perform
*	an unsupported operation.
*/
@SuppressWarnings("serial")
@ErrorType("unsupported")
public class PsyUnsupportedException
	extends PsyErrorException
{
	/**
	*	Constructs a new {@code exception} object with null as its detail message.
	*/
	public PsyUnsupportedException()
	{
	}

	/**
	*	Constructs a new {@code exception} object with the specified cause.
	*
	*	@param cause the cause.
	*/
	public PsyUnsupportedException(final Throwable cause)
	{
		super(cause);
	}
}
