package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	 The representation of {@code fileexists} error thrown when an attempt is made to create a file
*	 or directory and a file of that name already exists.
*/
@SuppressWarnings("serial")
@ErrorType("fileexists")
public class PsyFileExistsException
	extends PsyFileSystemErrorException
{
	/**
	*	Constructs a new {@code exception} object with null as its detail message.
	*/
	public PsyFileExistsException()
	{
	}

	/**
	*	Constructs a new {@code exception} object with the specified cause.
	*
	*	@param cause the cause.
	*/
	public PsyFileExistsException(final Throwable cause)
	{
		super(cause);
	}
}
