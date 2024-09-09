package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

@ErrorType("invalidregexp")
public class PsyInvalidRegExpException
	extends PsyErrorException
{
	/**
	*	Constructs a new {@code exception} object with null as its detail message.
	*/
	public PsyInvalidRegExpException()
	{
		super();
	}
}
