package coneforest.psylla.core;

import coneforest.psylla.runtime.*;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;

/**
*	The representation of {@code blockingqueue}.
*/
@Type("blockingqueue")
public final class PsyBlockingQueue
	implements
		PsyFormalQueue<PsyObject>,
		PsyCloseable
{
	/**
	*	Context action of the {@code blockingqueue} operator.
	*/
	@OperatorType("blockingqueue")
	public static final ContextAction PSY_BLOCKINGQUEUE
		=ContextAction.<PsyInteger>ofFunction(PsyBlockingQueue::new);

	private final ArrayBlockingQueue<PsyObject> queue;

	private boolean closed=false;

	public PsyBlockingQueue(final PsyInteger oCapacity)
		throws PsyRangeCheckException, PsyLimitCheckException
	{
		final var capacity=oCapacity.longValue();
		if(capacity>=Integer.MAX_VALUE)
			throw new PsyLimitCheckException();
		try
		{
			queue=new ArrayBlockingQueue<>((int)capacity);
		}
		catch(final IllegalArgumentException ex)
		{
			throw new PsyRangeCheckException();
		}
	}

	/**
	*	{@return the number of elements in this queue}
	*/
	@Override
	public int length()
	{
		return queue.size();
	}

	@Override
	public void psyGive(final PsyObject o)
		throws PsyInterruptException
	{
		try
		{
			queue.put(o);
		}
		catch(final InterruptedException ex)
		{
			throw new PsyInterruptException();
		}
	}

	@Override
	public void psyEnqueue(final PsyObject o)
		throws PsyInvalidStateException
	{
		try
		{
			queue.add(o);
		}
		catch(final IllegalStateException ex)
		{
			throw new PsyInvalidStateException();
		}
	}

	@Override
	public PsyObject psyDequeue()
		throws PsyInvalidStateException
	{
		try
		{
			return queue.remove();
		}
		catch(final IllegalStateException ex)
		{
			throw new PsyInvalidStateException();
		}
	}

	@Override
	public PsyObject psyTake()
		throws PsyInterruptException
	{
		try
		{
			return queue.take();
		}
		catch(final InterruptedException ex)
		{
			throw new PsyInterruptException();
		}
	}

	@Override
	public void psyClose()
	{
		closed=true;
	}

	@Override
	public void psyClear()
	{
		queue.clear();
	}

	@Override
	public Iterator<PsyObject> iterator()
	{
		return queue.iterator();
	}

	@Override
	public int capacity()
	{
		return queue.remainingCapacity()+length();
	}

	@Override
	public PsyStream psyStream()
	{
		return new PsyStream(queue.stream());
	}
}
