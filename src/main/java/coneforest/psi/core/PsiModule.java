package coneforest.psi.core;

public class PsiModule
	extends PsiDict
{
	/*public PsiModule(final String name)
	{
		super(name);
	}
	*/

	protected void registerOperators(final PsiOperator... operators)
	{
		for(final PsiOperator oOperator: operators)
			put(oOperator.getName(), oOperator);
	}
}
