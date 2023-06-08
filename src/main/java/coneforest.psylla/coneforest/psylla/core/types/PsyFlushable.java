package coneforest.psylla.core.types;

import coneforest.psylla.Type;
import coneforest.psylla.core.errors.PsyError;

/**
*	A representation of a {@code flushable}, a type of objects that can be flushed in some sense.
*/
@Type("flushable")
public interface PsyFlushable
	extends PsyObject
{

	/**
	*	Flush this {@code flushable}.
	*
	*	@throws PsyError when error occurs during flush.
	*/
	public void psyFlush()
		throws PsyError;

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity10<PsyFlushable>
				("flush", PsyFlushable::psyFlush),
		};

}
