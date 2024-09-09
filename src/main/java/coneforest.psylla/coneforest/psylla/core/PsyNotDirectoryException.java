package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	 The representation of {@code notdirectory} error thrown when a file system operation, intended
*	 for a directory, fails because the file is not a directory.
*/
@ErrorType("notdirectory")
public class PsyNotDirectoryException
	extends PsyFileSystemErrorException
{
	/**
	*	Constructs a new {@code exception} object with null as its detail message.
	*/
	public PsyNotDirectoryException()
	{
		super();
	}

	/**
	*	Constructs a new {@code exception} object with the specified cause.
	*/
	public PsyNotDirectoryException(final Throwable cause)
	{
		super(cause);
	}
}
