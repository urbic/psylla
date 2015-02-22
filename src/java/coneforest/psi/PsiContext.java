package coneforest.psi;

public class PsiContext
	extends PsiAbstractObject
{
	public PsiContext(Interpreter interpreter, Thread thread)
	{
		this.interpreter=interpreter;
		this.thread=thread;
	}

	public String getTypeName()
	{
		return "context";
	}

	public void start()
	{
		thread.start();
	}

	public void join()
		throws PsiException
	{
		try
		{
			thread.join();
		}
		catch(InterruptedException e)
		{
			throw new PsiException("interrupt");
		}
	}

	private final Interpreter interpreter;
	private final Thread thread;
}
