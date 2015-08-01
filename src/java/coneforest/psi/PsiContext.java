package coneforest.psi;

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
		try
		{
			join();
		}
		catch(InterruptedException e)
		{
			throw new PsiException("interrupt");
		}
	}

	@Override
	default public String toSyntaxString()
	{
		return "-context:"+getId()+"-";
	}

	public static void psiSleep(final PsiNumeric numeric)
		throws PsiException
	{
		try
		{
			java.util.concurrent.TimeUnit.NANOSECONDS.sleep((long)(1E9*numeric.doubleValue()));
		}
		catch(InterruptedException e)
		{
			throw new PsiException("interrupt");
		}
	}
}
