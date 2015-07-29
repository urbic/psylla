package coneforest.psi;

public class PsiContext
	implements PsiObject
{
	public PsiContext(Interpreter interpreter)
	{
		this.interpreter=interpreter;
	}

	public String getTypeName()
	{
		return "context";
	}

	public void start()
	{
		interpreter.start();
	}

	@Override
	public String toString()
	{
		return "-context:"+interpreter.getId()+"-";
	}

	public Interpreter getInterpreter()
	{
		return interpreter;
	}

	public void join()
		throws PsiException
	{
		try
		{
			interpreter.join();
		}
		catch(InterruptedException e)
		{
			throw new PsiException("interrupt");
		}
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

	private final Interpreter interpreter;
}
