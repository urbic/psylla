package coneforest.psylla.core;

/**
*	A representation of Ψ-{@code context}, an execution context.
*/
@coneforest.psylla.Type("context")
public interface PsyContext
	extends PsyObject
{

	public long getId();

	public void join()
		throws InterruptedException;

	default public void psyJoin()
		throws PsyException
	{
		if(this==psyCurrentContext())
			throw new PsyInvalidContextException();
		try
		{
			join();
		}
		catch(final InterruptedException e)
		{
			throw new PsyInterruptException();
		}
	}

	@Override
	default public String toSyntaxString()
	{
		return "%context="+getId()+"%";
	}

	/**
	*	Causes the currently executing context to sleep for the specified
	*	number of milliseconds.
	*
	*	@param oDuration the duration to sleep for in milliseconds.
	*	@throws PsyRangeCheckException when the duration is negative.
	*	@throws PsyInterruptException TODO.
	*/
	public static void psySleep(final PsyInteger oDuration)
		throws PsyException
	{
		try
		{
			Thread.sleep(oDuration.longValue());
		}
		catch(final IllegalArgumentException e)
		{
			throw new PsyRangeCheckException();
		}
		catch(final InterruptedException e)
		{
			throw new PsyInterruptException();
		}
	}

	/**
	*	Returns the currently executing context.
	*
	*	@return the currently executing context.
	*/
	public static PsyContext psyCurrentContext()
	{
		return (PsyContext)Thread.currentThread();
	}
}
