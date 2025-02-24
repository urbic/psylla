package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

@SuppressWarnings("serial")
@ErrorType("invalidexternal")
public class PsyInvalidExternalException
	extends PsyErrorException
{
	/**
	*	Constructs a new {@code exception} object with null as its detail message.
	*/
	public PsyInvalidExternalException()
	{
	}
}
