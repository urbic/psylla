package coneforest.psylla.core;

import coneforest.psylla.*;

/**
*	The representation of {@code closeable}, a type of objects that can be closed in some sense.
*/
@Type("closeable")
public interface PsyCloseable
	extends PsyObject
{

	/**
	*	Closes this object.
	*
	*	@throws PsyIOErrorException when I/O error occured during closing.
	*/
	public void psyClose()
		throws PsyIOErrorException;

	/**
	*	Context action of the {@code close} operator.
	*/
	@OperatorType("close")
	public static final ContextAction PSY_CLOSE
		=ContextAction.<PsyCloseable>ofConsumer(PsyCloseable::psyClose);
}
