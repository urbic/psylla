package coneforest.psi;

public class PsiCondition
	extends PsiAbstractObject
	implements PsiAtomic
{
	public String getTypeName()
	{
		return "condition";
	}

	public PsiCondition(java.util.concurrent.locks.Condition condition)
	{
		this.condition=condition;
	}

	public void psiNotify()
	{
		condition.signal();
	}

	public void psiWait()
		throws PsiException
	{
		try
		{
			condition.await();
		}
		catch(InterruptedException e)
		{
			throw new PsiException("interrupted");
		}
	}

	private java.util.concurrent.locks.Condition condition;
}
