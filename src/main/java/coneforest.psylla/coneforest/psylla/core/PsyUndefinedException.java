package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of the {@code undefined} error thrown by the operator accessing the
*	dictionary by the absent key or by the interpreter failing to resolve the command name against
*	the dictionary stack.
*/
@SuppressWarnings("serial")
@ErrorType("undefined")
public class PsyUndefinedException
	extends PsyErrorException
{
	/**
	*	Constructs a new {@code exception} object with null as its detail message.
	*/
	public PsyUndefinedException()
	{
	}

	/**
	*	Constructs a new {@code exception} object with the specified cause.
	*
	*	@param cause the cause.
	*/
	public PsyUndefinedException(final Throwable cause)
	{
		super(cause);
	}
}
