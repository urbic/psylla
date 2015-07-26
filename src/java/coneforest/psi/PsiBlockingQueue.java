package coneforest.psi;

public class PsiBlockingQueue
	extends PsiAbstractObject
	implements
		PsiQueuelike<PsiObject>,
		PsiCloseable
{
	public PsiBlockingQueue(PsiInteger integer)
		throws PsiException
	{
		long integerValue=integer.longValue();
		if(integerValue>=Integer.MAX_VALUE)
			throw new PsiException("limitcheck");
		try
		{
			queue=new java.util.concurrent.ArrayBlockingQueue<PsiObject>((int)integerValue);
		}
		catch(IllegalArgumentException e)
		{
			throw new PsiException("rangecheck");
		}
	}

	public String getTypeName()
	{
		return "blockingqueue";
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
			throw new PsiException("interrupt");
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
			throw new PsiException("interrupt");
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
