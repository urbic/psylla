package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	 The representation of {@code filenotfound} error thrown when an attempt is made to access a
*	 file that does not exist.
*/
@ErrorType("filenotfound")
public class PsyFileNotFoundException
	extends PsyFileSystemErrorException
{
	/**
	*	Constructs a new {@code exception} object with null as its detail message.
	*/
	public PsyFileNotFoundException()
	{
		super();
	}

	/**
	*	Constructs a new {@code exception} object with the specified cause.
	*/
	public PsyFileNotFoundException(final Throwable cause)
	{
		super(cause);
	}
}
