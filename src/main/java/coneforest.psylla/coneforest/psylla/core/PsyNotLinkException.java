package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	 The representation of {@code notlink} error thrown when a file system operation, intended
*	 for a symlink, fails because the file is not a symlink.
*/
@SuppressWarnings("serial")
@ErrorType("notlink")
public class PsyNotLinkException
	extends PsyFileSystemErrorException
{
	/**
	*	Constructs a new {@code exception} object with null as its detail message.
	*/
	public PsyNotLinkException()
	{
	}

	/**
	*	Constructs a new {@code exception} object with the specified cause.
	*
	*	@param cause the cause.
	*/
	public PsyNotLinkException(final Throwable cause)
	{
		super(cause);
	}
}
