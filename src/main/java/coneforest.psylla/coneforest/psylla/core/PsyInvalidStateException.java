package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of the {@code invalidstate} error thrown if the execution of the operator is
*	not possible with the current state of the operand or environment.
*/
@SuppressWarnings("serial")
@ErrorType("invalidstate")
public class PsyInvalidStateException
	extends PsyErrorException
{
	/**
	*	Constructs a new {@code exception} object with null as its detail message.
	*/
	public PsyInvalidStateException()
	{
	}
}
