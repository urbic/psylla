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

	private java.util.concurrent.locks.Condition condition;
}
