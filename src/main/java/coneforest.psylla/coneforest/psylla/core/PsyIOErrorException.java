package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of the {@code ioerror} error thrown when I/O exception of some sort has
*	occurred.
*/
@SuppressWarnings("serial")
@ErrorType("ioerror")
public class PsyIOErrorException
	extends PsyErrorException
{
	/**
	*	Constructs a new {@code exception} object with null as its detail message.
	*/
	public PsyIOErrorException()
	{
	}

	/**
	*	Constructs a new {@code exception} object with the specified cause.
	*
	*	@param cause the cause.
	*/
	public PsyIOErrorException(final Throwable cause)
	{
		super(cause);
	}
}
