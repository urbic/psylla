package coneforest.psylla.core;

import coneforest.psylla.runtime.*;
import java.util.concurrent.locks.Condition;

/**
*	The representation of {@code condition}.
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
	public PsyCondition(final Condition condition)
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
		throws PsyInterruptException
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

	private final Condition condition;

	/**
	*	Context action of the {@code notify} operator.
	*/
	@OperatorType("notify")
	public static final ContextAction PSY_NOTIFY
		=ContextAction.<PsyCondition>ofConsumer(PsyCondition::psyNotify);

	/**
	*	Context action of the {@code wait} operator.
	*/
	@OperatorType("wait")
	public static final ContextAction PSY_WAIT
		=ContextAction.<PsyCondition>ofConsumer(PsyCondition::psyWait);
}
