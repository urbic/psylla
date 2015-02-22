package coneforest.psi;

public class PsiLock
	extends PsiAbstractObject
	implements PsiAtomic
	//implements java.util.concurrent.locks.Lock
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

	private java.util.concurrent.locks.ReentrantLock lock
		=new java.util.concurrent.locks.ReentrantLock();
}
