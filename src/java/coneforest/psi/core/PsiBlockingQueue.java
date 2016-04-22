package coneforest.psi.core;

public class PsiBlockingQueue
	implements
		PsiQueuelike<PsiObject>,
		PsiCloseable
{
	public PsiBlockingQueue(PsiInteger integer)
		throws PsiException
	{
		long integerValue=integer.longValue();
		if(integerValue>=Integer.MAX_VALUE)
			throw new PsiLimitCheckException();
		try
		{
			queue=new java.util.concurrent.ArrayBlockingQueue<PsiObject>((int)integerValue);
		}
		catch(IllegalArgumentException e)
		{
			throw new PsiRangeCheckException();
		}
	}

	@Override
	public String getTypeName()
	{
		return "blockingqueue";
	}

	/**
	 *	Returns the number of elements in this queue.
	 *	@return the number of elements in this queue.
	 */
	@Override
	public int length()
	{
		return queue.size();
	}

	@Override
	public void psiGive(final PsiObject o)
		throws PsiException
	{
		try
		{
			queue.put(o);
		}
		catch(InterruptedException e)
		{
			throw new PsiInterruptException();
		}
	}

	@Override
	public void psiEnqueue(final PsiObject o)
		throws PsiException
	{
		try
		{
			queue.add(o);
		}
		catch(IllegalStateException e)
		{
			throw new PsiInvalidStateException();
		}
	}

	@Override
	public PsiObject psiDequeue()
		throws PsiException
	{
		try
		{
			return queue.remove();
		}
		catch(IllegalStateException e)
		{
			throw new PsiInvalidStateException();
		}
	}

	@Override
	public PsiObject psiTake()
		throws PsiException
	{
		try
		{
			return queue.take();
		}
		catch(InterruptedException e)
		{
			throw new PsiInterruptException();
		}
	}

	@Override
	public void psiClose()
	{
		closed=true;
	}

	@Override
	public void psiClear()
	{
		queue.clear();
	}

	@Override
	public java.util.Iterator<PsiObject> iterator()
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

	private java.util.concurrent.ArrayBlockingQueue<PsiObject> queue;
	private boolean closed=false;
}
