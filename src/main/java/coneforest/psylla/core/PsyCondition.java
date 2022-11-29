package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of {@code condition} object.
*/
@Type("condition")
public class PsyCondition
	implements PsyObject
{

	/**
	*	Constructs a {@code condition} object that wraps given condition.
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
		throws PsyErrorException
	{
		try
		{
			condition.await();
		}
		catch(final InterruptedException ex)
		{
			throw new PsyInterruptException();
		}
	}

	private final java.util.concurrent.locks.Condition condition;

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity10<PsyCondition>("notify", PsyCondition::psyNotify),
			new PsyOperator.Arity10<PsyCondition>("wait", PsyCondition::psyWait),
		};

}
