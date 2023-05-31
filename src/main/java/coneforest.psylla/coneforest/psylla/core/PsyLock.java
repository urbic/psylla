package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of {@code lock}.
*/
@Type("lock")
public class PsyLock
	implements PsyObject
{

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
	*
	*	@return {@code true} if current context holds this lock and {@code
	*	false} otherwise.
	*/
	public boolean isHeldByCurrentThread()
	{
		return lock.isHeldByCurrentThread();
	}

	/**
	*	Returns a {@code condition} object for use with this lock.
	*
	*	@return a {@code condition} object.
	*/
	public PsyCondition psyCondition()
	{
		return new PsyCondition(lock.newCondition());
	}

	private final java.util.concurrent.locks.ReentrantLock lock
		=new java.util.concurrent.locks.ReentrantLock();

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity11<PsyLock>
				("condition", PsyLock::psyCondition),
			new PsyOperator.Arity01
				("lock", PsyLock::new),
		};

}
