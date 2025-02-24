package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of the {@code invalidexit} error thrown by the {@code exit} operator when
*	invoked outside the cyclic context.
*/
@SuppressWarnings("serial")
@ErrorType("invalidexit")
public class PsyInvalidExitException
	extends PsyErrorException
{
	/**
	*	Constructs a new {@code exception} object with null as its detail message.
	*/
	public PsyInvalidExitException()
	{
	}
}
