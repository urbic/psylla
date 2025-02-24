package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of the {@code rangecheck} error thrown when the operand has the value outside
*	the range acceptable for the operator.
*/
@SuppressWarnings("serial")
@ErrorType("rangecheck")
public class PsyRangeCheckException
	extends PsyErrorException
{
	/**
	*	Constructs a new {@code exception} object with null as its detail message.
	*/
	public PsyRangeCheckException()
	{
	}

	/**
	*	Constructs a new {@code exception} object with the specified cause.
	*
	*	@param cause the cause.
	*/
	public PsyRangeCheckException(final Throwable cause)
	{
		super(cause);
	}
}
