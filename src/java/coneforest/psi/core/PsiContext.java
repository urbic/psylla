package coneforest.psi.core;

public interface PsiContext
	extends PsiObject
{
	@Override
	default public String getTypeName()
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
		catch(InterruptedException e)
		{
			throw new PsiInterruptException();
		}
	}

	@Override
	default public String toSyntaxString()
	{
		return "-context:"+getId()+"-";
	}

	public static void psiSleep(final PsiInteger integer)
		throws PsiException
	{
		try
		{
			Thread.sleep(integer.longValue());
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

	public static PsiContext psiCurrentContext()
	{
		return (PsiContext)Thread.currentThread();
	}
}
