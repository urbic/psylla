package coneforest.psylla.core;

import coneforest.psylla.*;

/**
*	The representation of {@code formalqueue}, an abstraction of a queue of {@code object}s.
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
	*	@throws PsyErrorException when this queue is empty.
	*/
	public T psyDequeue()
		throws PsyErrorException;

	/**
	*	Inserts an element into this queue.
	*
	*	@param o the element to enqueue.
	*	@throws PsyErrorException when the element can not be inserted without violation of the capacity
	*	restrictions.
	*/
	public void psyEnqueue(final T o)
		throws PsyErrorException;

	public T psyTake()
		throws PsyErrorException;

	public void psyGive(final T o)
		throws PsyErrorException;

	/**
	*	Context action of the {@code dequeue} operator.
	*/
	@OperatorType("dequeue")
	public static final ContextAction PSY_DEQUEUE
		=ContextAction.<PsyFormalQueue>ofFunction(PsyFormalQueue::psyDequeue);

	/**
	*	Context action of the {@code enqueue} operator.
	*/
	@OperatorType("enqueue")
	public static final ContextAction PSY_ENQUEUE
		=ContextAction.<PsyFormalQueue, PsyObject>ofBiConsumer(PsyFormalQueue::psyEnqueue);

	/**
	*	Context action of the {@code give} operator.
	*/
	@OperatorType("give")
	public static final ContextAction PSY_GIVE
		=ContextAction.<PsyFormalQueue, PsyObject>ofBiConsumer(PsyFormalQueue::psyGive);

	/**
	*	Context action of the {@code take} operator.
	*/
	@OperatorType("take")
	public static final ContextAction PSY_TAKE
		=ContextAction.<PsyFormalQueue>ofFunction(PsyFormalQueue::psyTake);
}
