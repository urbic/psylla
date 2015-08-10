package coneforest.psi;

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
	public void psiGive(PsiObject obj)
		throws PsiException
	{
		try
		{
			queue.put(obj);
		}
		catch(InterruptedException e)
		{
			throw new PsiInterruptException();
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

	private java.util.concurrent.ArrayBlockingQueue<PsiObject> queue;
	private boolean closed=false;
}
