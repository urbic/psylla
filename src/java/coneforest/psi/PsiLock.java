package coneforest.psi;

public class PsiLock
	extends PsiAbstractObject
	implements PsiAtomic
{
	public String getTypeName()
	{
		return "lock";
	}

	public void lock()
	{
		lock.lock();
	}

	public void unlock()
	{
		lock.unlock();
	}

	public boolean isHeldByCurrentThread()
	{
		return lock.isHeldByCurrentThread();
	}

	public PsiCondition psiCondition()
	{
		return new PsiCondition(lock.newCondition());
	}

	private final java.util.concurrent.locks.ReentrantLock lock
		=new java.util.concurrent.locks.ReentrantLock();
}
