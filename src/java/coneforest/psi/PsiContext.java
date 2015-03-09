package coneforest.psi;

public class PsiContext
	extends PsiAbstractObject
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
		return "--context:"+interpreter.getId()+"--";
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

	private final Interpreter interpreter;
}
