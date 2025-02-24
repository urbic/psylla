package coneforest.psylla.core;

import coneforest.psylla.runtime.*;
import java.util.concurrent.locks.ReentrantLock;

/**
*	The representation of {@code lock}.
*/
@Type("lock")
public class PsyLock
	implements PsyObject
{
	/**
	*	Context action of the {@code condition} operator.
	*/
	@OperatorType("condition")
	public static final ContextAction PSY_CONDITION
		=ContextAction.<PsyLock>ofFunction(PsyLock::psyCondition);

	/**
	*	Context action of the {@code lock} operator.
	*/
	@OperatorType("lock")
	public static final ContextAction PSY_LOCK
		=ContextAction.ofSupplier(PsyLock::new);

	private final ReentrantLock lock=new ReentrantLock();

	public PsyLock()
	{
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
	*
	*	@return {@code true} if current context holds this lock and {@code
	*	false} otherwise.
	*/
	public boolean isHeldByCurrentThread()
	{
		return lock.isHeldByCurrentThread();
	}

	/**
	*	{@return a {@code condition} object for use with this lock}
	*/
	public PsyCondition psyCondition()
	{
		return new PsyCondition(lock.newCondition());
	}
}
