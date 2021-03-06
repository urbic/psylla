package coneforest.psylla.core;

/**
*	A representation of Ψ-{@code flushable}, a type of objects that can be
*	flushed in some sense.
*/
@coneforest.psylla.Type("flushable")
public interface PsyFlushable
	extends PsyObject
{

	/**
	*	Flush this object.
	*
	*	@throws PsyException when error occurs during flush.
	*/
	public void psyFlush()
		throws PsyException;
}
