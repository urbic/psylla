package coneforest.psylla.core;
import coneforest.psylla.*;

@Type("blockingqueue")
public class PsyBlockingQueue
	implements
		PsyQueuelike<PsyObject>,
		PsyCloseable
{
	public PsyBlockingQueue(final PsyInteger oCapacity)
		throws PsyException
	{
		final var capacity=oCapacity.longValue();
		if(capacity>=Integer.MAX_VALUE)
			throw new PsyLimitCheckException();
		try
		{
			queue=new java.util.concurrent.ArrayBlockingQueue<PsyObject>((int)capacity);
		}
		catch(final IllegalArgumentException e)
		{
			throw new PsyRangeCheckException();
		}
	}

	/**
	*	Returns the number of elements in this queue.
	*
	*	@return the number of elements in this queue.
	*/
	@Override
	public int length()
	{
		return queue.size();
	}

	@Override
	public void psyGive(final PsyObject o)
		throws PsyException
	{
		try
		{
			queue.put(o);
		}
		catch(final InterruptedException e)
		{
			throw new PsyInterruptException();
		}
	}

	@Override
	public void psyEnqueue(final PsyObject o)
		throws PsyException
	{
		try
		{
			queue.add(o);
		}
		catch(final IllegalStateException e)
		{
			throw new PsyInvalidStateException();
		}
	}

	@Override
	public PsyObject psyDequeue()
		throws PsyException
	{
		try
		{
			return queue.remove();
		}
		catch(final IllegalStateException e)
		{
			throw new PsyInvalidStateException();
		}
	}

	@Override
	public PsyObject psyTake()
		throws PsyException
	{
		try
		{
			return queue.take();
		}
		catch(final InterruptedException e)
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
	public java.util.Iterator<PsyObject> iterator()
	{
		return queue.iterator();
	}

	@Override
	public int capacity()
	{
		synchronized(queue)
		{
			return queue.remainingCapacity()+length();
		}
	}

	@Override
	public PsyStream psyStream()
	{
		return new PsyStream(queue.stream());
	}

	private final java.util.concurrent.ArrayBlockingQueue<PsyObject> queue;
	private boolean closed=false;

}
