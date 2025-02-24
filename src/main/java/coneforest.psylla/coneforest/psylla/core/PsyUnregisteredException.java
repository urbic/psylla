package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

@SuppressWarnings("serial")
@ErrorType("unregistered")
public class PsyUnregisteredException
	extends PsyErrorException
{
	/**
	*	Constructs a new {@code exception} object with null as its detail message.
	*/
	public PsyUnregisteredException()
	{
	}
}
