package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	 The representation of {@code concurrentmodification} error thrown when concurrent modification
*	 detected.
*/
@SuppressWarnings("serial")
@ErrorType("concurrentmodification")
public class PsyConcurrentModificationException
	extends PsyErrorException
{
	/**
	*	Constructs a new {@code exception} object with null as its detail message.
	*/
	public PsyConcurrentModificationException()
	{
	}

	/**
	*	Constructs a new {@code exception} object with the specified cause.
	*
	*	@param cause the cause.
	*/
	public PsyConcurrentModificationException(final Throwable cause)
	{
		super(cause);
	}
}
