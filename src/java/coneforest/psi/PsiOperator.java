package coneforest.psi;

/**
 *
 */
public abstract class PsiOperator extends PsiObject
{
	public PsiOperator()
	{
		super();
		setExecutable();
	}

	@Override
	public String getTypeName() { return "operator"; }

	public void execute(Interpreter interpreter)
	{
		if(isExecutable())
			invoke(interpreter);
		else
			super.execute(interpreter);
	}

	public String toString()
	{
		return "--"+getName()+"--";
	}

	public String getName()
	{
		return getClass().getSimpleName().substring(1);
	}
}
