package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of the {@code securityerror} error thrown by the security manager to indicate
*	a security violation.
*/
@SuppressWarnings("serial")
@ErrorType("securityerror")
public class PsySecurityErrorException
	extends PsyErrorException
{
	/**
	*	Constructs a new {@code exception} object with null as its detail message.
	*/
	public PsySecurityErrorException()
	{
	}

	/**
	*	Constructs a new {@code exception} object with the specified cause.
	*
	*	@param cause the cause.
	*/
	public PsySecurityErrorException(final Throwable cause)
	{
		super(cause);
	}
}
