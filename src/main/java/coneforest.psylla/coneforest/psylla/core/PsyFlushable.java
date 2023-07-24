package coneforest.psylla.core;

import coneforest.psylla.*;

/**
*	A representation of a {@code flushable}, an object that can be flushed in some sense.
*/
@Type("flushable")
public interface PsyFlushable
	extends PsyObject
{
	/**
	*	Flush this {@code flushable}.
	*
	*	@throws PsyIOErrorException when I/O error occurs during flush.
	*/
	public void psyFlush()
		throws PsyIOErrorException;

	/**
	*	Context action of the {@code flush} operator.
	*/
	@OperatorType("flush")
	public static final ContextAction PSY_FLUSH
		=ContextAction.<PsyFlushable>ofConsumer(PsyFlushable::psyFlush);
}
