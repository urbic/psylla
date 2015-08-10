package coneforest.psi;

/**
 *	A representation of Ψ-<code class="type">condition</code> object.
 */
public class PsiCondition
	implements PsiAtomic
{
	/**
	 *	@return a string <code class="constant">"condition"</code>.
	 */
	public String getTypeName()
	{
		return "condition";
	}

	/**
	 *	Constructs a Ψ-<code class="type">condition</code> object that wraps
	 *	given condition.
	 *
	 * 	@param condition a condition.
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
	 *	@throws PsiException when current context is interrupted.
	 */
	public void psiWait()
		throws PsiException
	{
		try
		{
			condition.await();
		}
		catch(InterruptedException e)
		{
			throw new PsiInterruptException();
		}
	}

	private final java.util.concurrent.locks.Condition condition;
}
