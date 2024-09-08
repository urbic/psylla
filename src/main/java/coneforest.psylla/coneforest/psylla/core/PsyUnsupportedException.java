package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

@ErrorType("unsupported")
public class PsyUnsupportedException
	extends PsyErrorException
{
	/**
	*	Constructs a new {@code exception} object with null as its detail message.
	*/
	public PsyUnsupportedException()
	{
		super();
	}
}
