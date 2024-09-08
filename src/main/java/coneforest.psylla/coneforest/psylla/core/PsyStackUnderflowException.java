package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of the {@code stackunderflow} error thrown when the operand stack contains
*	fewer operands than the operator expects.
*/
@ErrorType("stackunderflow")
public class PsyStackUnderflowException
	extends PsyErrorException
{
	/**
	*	Constructs a new {@code exception} object with null as its detail message.
	*/
	public PsyStackUnderflowException()
	{
		super();
	}
}
