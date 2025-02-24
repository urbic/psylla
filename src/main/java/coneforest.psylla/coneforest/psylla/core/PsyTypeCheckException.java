package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of the {@code typecheck} error thrown when the operand has the type that is
*	unexpected for the operator.
*/
@SuppressWarnings("serial")
@ErrorType("typecheck")
public class PsyTypeCheckException
	extends PsyErrorException
{
	/**
	*	Constructs a new {@code exception} object with null as its detail message.
	*/
	public PsyTypeCheckException()
	{
	}

	/**
	*	Constructs a new {@code exception} object with the specified cause.
	*
	*	@param cause the cause.
	*/
	public PsyTypeCheckException(final Throwable cause)
	{
		super(cause);
	}
}
