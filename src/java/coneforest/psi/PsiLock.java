package coneforest.psi;

/**
 *	A representation of Ψ-<code class="type">lock</code> object.
 */
public class PsiLock
	extends PsiAbstractObject
	implements PsiAtomic
{
	/**
	 *	@return a string <code class="constant">"lock"</code>.
	 */
	public String getTypeName()
	{
		return "lock";
	}

	/**
	 *	Acquires the lock.
	 */
	public void lock()
	{
		lock.lock();
	}

	/**
	 *	Releases the lock.
	 */
	public void unlock()
	{
		lock.unlock();
	}

	/**
	 *	Queries if this lock is held by the current thread.
	 */
	public boolean isHeldByCurrentThread()
	{
		return lock.isHeldByCurrentThread();
	}

	/**
	 *	Returns a Ψ-<code class="type">condition</code> for use with this lock.
	 */
	public PsiCondition psiCondition()
	{
		return new PsiCondition(lock.newCondition());
	}

	private final java.util.concurrent.locks.ReentrantLock lock
		=new java.util.concurrent.locks.ReentrantLock();
}
