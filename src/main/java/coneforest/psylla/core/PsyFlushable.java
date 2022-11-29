package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of a {@code flushable}, a type of objects that can be
*	flushed in some sense.
*/
@Type("flushable")
public interface PsyFlushable
	extends PsyObject
{

	/**
	*	Flush this {@code flushable}.
	*
	*	@throws PsyErrorException when error occurs during flush.
	*/
	public void psyFlush()
		throws PsyErrorException;

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity10<PsyFlushable>
				("flush", PsyFlushable::psyFlush),
		};

}
