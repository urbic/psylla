package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of Ψ-{@code queuelike}, an abstraction of a queue of
*	Ψ-{@code object}s.
*
*	@param <T> a type of contained objects.
*/
@Type("queuelike")
public interface PsyQueuelike<T extends PsyObject>
	extends
		PsyBounded,
		PsyContainer<T>
{

	/**
	*	Removes and returns the head of this queue.
	*
	*	@return a head of this queue.
	*	@throws PsyException when this queue is empty.
	*/
	public T psyDequeue()
		throws PsyException;

	/**
	*	Inserts an element into this queue.
	*
	*	@param o the element to enqueue.
	*	@throws PsyException when the element can not be inserted without
	*	violation of the capacity restrictions.
	*/
	public void psyEnqueue(final T o)
		throws PsyException;

	public T psyTake()
		throws PsyException;

	public void psyGive(final T o)
		throws PsyException;

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity11<PsyQueuelike>("dequeue", PsyQueuelike::psyDequeue),
			new PsyOperator.Arity20<PsyQueuelike, PsyObject>("enqueue", PsyQueuelike::psyEnqueue),
			new PsyOperator.Arity20<PsyQueuelike, PsyObject>("give", PsyQueuelike::psyGive),
			new PsyOperator.Arity11<PsyQueuelike>("take", PsyQueuelike::psyTake),
		};

}
