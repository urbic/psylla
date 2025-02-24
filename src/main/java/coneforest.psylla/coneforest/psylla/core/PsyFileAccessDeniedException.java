package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of the {@code ioerror} error thrown when a file system operation is denied,
*	typically due to a file permission or other access check.
*/
@SuppressWarnings("serial")
@ErrorType("fileaccessdenied")
public class PsyFileAccessDeniedException
	extends PsyFileSystemErrorException
{
	/**
	*	Constructs a new {@code exception} object with null as its detail message.
	*/
	public PsyFileAccessDeniedException()
	{
	}

	/**
	*	Constructs a new {@code exception} object with the specified cause.
	*
	*	@param cause the cause.
	*/
	public PsyFileAccessDeniedException(final Throwable cause)
	{
		super(cause);
	}
}
