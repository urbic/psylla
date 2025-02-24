package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

@SuppressWarnings("serial")
@ErrorType("invalidcontext")
public class PsyInvalidContextException
	extends PsyErrorException
{
	/**
	*	Constructs a new {@code exception} object with null as its detail message.
	*/
	public PsyInvalidContextException()
	{
	}
}
