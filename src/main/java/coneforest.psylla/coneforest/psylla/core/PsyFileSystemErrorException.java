package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

@ErrorType("filesystemerror")
public class PsyFileSystemErrorException
	extends PsyIOErrorException
{
	/**
	*	Constructs a new {@code exception} object with null as its detail message.
	*/
	public PsyFileSystemErrorException()
	{
		super();
	}

	/**
	*	Constructs a new {@code exception} object with the specified cause.
	*/
	public PsyFileSystemErrorException(final Throwable cause)
	{
		super(cause);
	}
}
