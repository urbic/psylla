package coneforest.psi.core;

/**
*	A representation of Ψ-{@code context}, an execution context.
*/
public interface PsiContext
	extends PsiObject
{
	/**
	*	@return a string {@code "context"}.
	*/
	@Override
	default public String typeName()
	{
		return "context";
	}

	public long getId();

	public void join()
		throws InterruptedException;

	default public void psiJoin()
		throws PsiException
	{
		if(this==psiCurrentContext())
			throw new PsiInvalidContextException();
		try
		{
			join();
		}
		catch(final InterruptedException e)
		{
			throw new PsiInterruptException();
		}
	}

	@Override
	default public String toSyntaxString()
	{
		return "|context="+getId()+"|";
	}

	/**
	*	Causes the currently executing context to sleep for the specified
	*	number of milliseconds.
	*
	*	@param oDuration the duration to sleep for in milliseconds.
	*	@throws PsiRangeCheckException when the duration is negative.
	*	@throws PsiInterruptException TODO.
	*/
	public static void psiSleep(final PsiInteger oDuration)
		throws PsiException
	{
		try
		{
			Thread.sleep(oDuration.longValue());
		}
		catch(IllegalArgumentException e)
		{
			throw new PsiRangeCheckException();
		}
		catch(InterruptedException e)
		{
			throw new PsiInterruptException();
		}
	}

	/**
	*	Returns the currently executing context.
	*
	*	@return the currently executing context.
	*/
	public static PsiContext psiCurrentContext()
	{
		return (PsiContext)Thread.currentThread();
	}
}
