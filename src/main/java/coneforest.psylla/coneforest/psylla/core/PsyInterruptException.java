package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

@ErrorType("interrupt")
public class PsyInterruptException
	extends PsyErrorException
{
	/**
	*	Constructs a new {@code exception} object with null as its detail message.
	*/
	public PsyInterruptException()
	{
		super();
	}
}
