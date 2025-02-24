package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of the {@code undefinedresult} error thrown when a result of an operation is
*	meaningless.
*/
@SuppressWarnings("serial")
@ErrorType("undefinedresult")
public class PsyUndefinedResultException
	extends PsyErrorException
{
	/**
	*	Constructs a new {@code exception} object with null as its detail message.
	*/
	public PsyUndefinedResultException()
	{
	}
}
