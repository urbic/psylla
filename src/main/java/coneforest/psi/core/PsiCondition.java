package coneforest.psi.core;

/**
*	A representation of Ψ-{@code condition} object.
*/
public class PsiCondition
	implements PsiObject
{
	/**
	*	@return a string {@code "condition"}.
	*/
	public String typeName()
	{
		return "condition";
	}

	/**
	*	Constructs a Ψ-{@code condition} object that wraps given condition.
	*
	*	@param condition a condition.
	*/
	public PsiCondition(final java.util.concurrent.locks.Condition condition)
	{
		this.condition=condition;
	}

	public void psiNotify()
	{
		condition.signal();
	}

	/**
	*	Causes the current context to wait until it is notified or interrupted.
	*
	*	@throws PsiInterruptException when current context is interrupted.
	*/
	public void psiWait()
		throws PsiException
	{
		try
		{
			condition.await();
		}
		catch(final InterruptedException e)
		{
			throw new PsiInterruptException();
		}
	}

	private final java.util.concurrent.locks.Condition condition;
}
