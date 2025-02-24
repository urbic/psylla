package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of the {@code limitcheck} error thrown when the system limits exceeded.
*/
@SuppressWarnings("serial")
@ErrorType("limitcheck")
public class PsyLimitCheckException
	extends PsyErrorException
{
	/**
	*	Constructs a new {@code exception} object with null as its detail message.
	*/
	public PsyLimitCheckException()
	{
	}
}
