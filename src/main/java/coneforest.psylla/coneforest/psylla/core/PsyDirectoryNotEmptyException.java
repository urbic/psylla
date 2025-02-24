package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

@SuppressWarnings("serial")
@ErrorType("directorynotempty")
public class PsyDirectoryNotEmptyException
	extends PsyFileSystemErrorException
{
	/**
	*	Constructs a new {@code exception} object with null as its detail message.
	*/
	public PsyDirectoryNotEmptyException()
	{
	}

	/**
	*	Constructs a new {@code exception} object with the specified cause.
	*
	*	@param cause the cause.
	*/
	public PsyDirectoryNotEmptyException(final Throwable cause)
	{
		super(cause);
	}
}
