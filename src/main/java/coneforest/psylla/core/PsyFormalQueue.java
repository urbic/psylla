package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of {@code formalqueue}, an abstraction of a queue of
*	{@code object}s.
*
*	@param <T> a type of contained objects.
*/
@Type("formalqueue")
public interface PsyFormalQueue<T extends PsyObject>
	extends
		PsyBounded,
		PsyContainer<T>
{

	/**
	*	Removes and returns the head of this queue.
	*
	*	@return a head of this queue.
	*
	*	@throws PsyErrorException when this queue is empty.
	*/
	public T psyDequeue()
		throws PsyErrorException;

	/**
	*	Inserts an element into this queue.
	*
	*	@param o the element to enqueue.
	*
	*	@throws PsyErrorException when the element can not be inserted without
	*	violation of the capacity restrictions.
	*/
	public void psyEnqueue(final T o)
		throws PsyErrorException;

	public T psyTake()
		throws PsyErrorException;

	public void psyGive(final T o)
		throws PsyErrorException;

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity11<PsyFormalQueue>
				("dequeue", PsyFormalQueue::psyDequeue),
			new PsyOperator.Arity20<PsyFormalQueue, PsyObject>
				("enqueue", PsyFormalQueue::psyEnqueue),
			new PsyOperator.Arity20<PsyFormalQueue, PsyObject>
				("give", PsyFormalQueue::psyGive),
			new PsyOperator.Arity11<PsyFormalQueue>
				("take", PsyFormalQueue::psyTake),
		};

}
