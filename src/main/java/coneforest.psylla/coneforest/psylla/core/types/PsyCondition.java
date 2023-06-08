package coneforest.psylla.core.types;

import coneforest.psylla.Type;
import coneforest.psylla.core.errors.PsyInterrupt;
import java.util.concurrent.locks.Condition;

/**
*	A representation of {@code condition}.
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
	*	@throws PsyInterrupt when current context is interrupted.
	*/
	public void psyWait()
		throws PsyInterrupt
	{
		try
		{
			condition.await();
		}
		catch(final InterruptedException ex)
		{
			throw new PsyInterrupt();
		}
	}

	private final Condition condition;

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity10<PsyCondition>("notify", PsyCondition::psyNotify),
			new PsyOperator.Arity10<PsyCondition>("wait", PsyCondition::psyWait),
		};

}
