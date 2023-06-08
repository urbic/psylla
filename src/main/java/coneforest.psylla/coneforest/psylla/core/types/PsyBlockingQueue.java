package coneforest.psylla.core.types;

import coneforest.psylla.Type;
import coneforest.psylla.core.errors.PsyError;
import coneforest.psylla.core.errors.PsyInterrupt;
import coneforest.psylla.core.errors.PsyInvalidState;
import coneforest.psylla.core.errors.PsyLimitCheck;
import coneforest.psylla.core.errors.PsyRangeCheck;
import java.util.concurrent.ArrayBlockingQueue;

@Type("blockingqueue")
public class PsyBlockingQueue
	implements
		PsyFormalQueue<PsyObject>,
		PsyCloseable
{
	public PsyBlockingQueue(final PsyInteger oCapacity)
		throws PsyError
	{
		final var capacity=oCapacity.longValue();
		if(capacity>=Integer.MAX_VALUE)
			throw new PsyLimitCheck();
		try
		{
			queue=new ArrayBlockingQueue<PsyObject>((int)capacity);
		}
		catch(final IllegalArgumentException ex)
		{
			throw new PsyRangeCheck();
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
		throws PsyError
	{
		try
		{
			queue.put(o);
		}
		catch(final InterruptedException ex)
		{
			throw new PsyInterrupt();
		}
	}

	@Override
	public void psyEnqueue(final PsyObject o)
		throws PsyError
	{
		try
		{
			queue.add(o);
		}
		catch(final IllegalStateException ex)
		{
			throw new PsyInvalidState();
		}
	}

	@Override
	public PsyObject psyDequeue()
		throws PsyError
	{
		try
		{
			return queue.remove();
		}
		catch(final IllegalStateException ex)
		{
			throw new PsyInvalidState();
		}
	}

	@Override
	public PsyObject psyTake()
		throws PsyError
	{
		try
		{
			return queue.take();
		}
		catch(final InterruptedException ex)
		{
			throw new PsyInterrupt();
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

	private final ArrayBlockingQueue<PsyObject> queue;
	private boolean closed=false;

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity11<PsyInteger>
				("blockingqueue", PsyBlockingQueue::new),
		};

}
