package coneforest.psi.core;

public class PsiModule
	extends PsiDict
{
	protected void registerOperators(PsiOperator... operators)
	{
		for(PsiOperator oOperator: operators)
			put(oOperator.getName(), oOperator);
	}
}
