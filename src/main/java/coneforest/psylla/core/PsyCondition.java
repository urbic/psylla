package coneforest.psylla.core;

/**
*	A representation of Ψ-{@code condition} object.
*/
@coneforest.psylla.Type("condition")
public class PsyCondition
	implements PsyObject
{

	/**
	*	Constructs a Ψ-{@code condition} object that wraps given condition.
	*
	*	@param condition a condition.
	*/
	public PsyCondition(final java.util.concurrent.locks.Condition condition)
	{
		this.condition=condition;
	}

	public void psyNotify()
	{
		condition.signal();
	}

	/**
	*	Causes the current context to wait until it is notified or interrupted.
	*
	*	@throws PsyInterruptException when current context is interrupted.
	*/
	public void psyWait()
		throws PsyException
	{
		try
		{
			condition.await();
		}
		catch(final InterruptedException e)
		{
			throw new PsyInterruptException();
		}
	}

	private final java.util.concurrent.locks.Condition condition;
}
