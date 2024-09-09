package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

@ErrorType("directorynotempty")
public class PsyDirectoryNotEmptyException
	extends PsyFileSystemErrorException
{
	/**
	*	Constructs a new {@code exception} object with null as its detail message.
	*/
	public PsyDirectoryNotEmptyException()
	{
		super();
	}

	/**
	*	Constructs a new {@code exception} object with the specified cause.
	*/
	public PsyDirectoryNotEmptyException(final Throwable cause)
	{
		super(cause);
	}
}
