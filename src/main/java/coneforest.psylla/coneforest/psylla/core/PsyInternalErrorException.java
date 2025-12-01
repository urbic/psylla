package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of the {@code internalerror} error thrown to indicate some unexpected
*	internal error has occurred in the Psylla runtime.
*/
@SuppressWarnings("serial")
@ErrorType("internalerror")
public class PsyInternalErrorException
	extends PsyErrorException
{
	/**
	*	Constructs a new {@code internalerror} object with null as its detail message.
	*/
	public PsyInternalErrorException()
	{
	}
}
