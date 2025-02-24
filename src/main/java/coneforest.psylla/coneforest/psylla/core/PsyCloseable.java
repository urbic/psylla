package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of {@code closeable}, a type of objects that can be closed in some sense.
*/
@Type("closeable")
public interface PsyCloseable
	extends PsyObject
{
	/**
	*	Context action of the {@code close} operator.
	*/
	@OperatorType("close")
	public static final ContextAction PSY_CLOSE
		=ContextAction.<PsyCloseable>ofConsumer(PsyCloseable::psyClose);

	/**
	*	Closes this object.
	*
	*	@throws PsyErrorException when error occured during closing.
	*/
	public void psyClose()
		throws PsyErrorException;

}
