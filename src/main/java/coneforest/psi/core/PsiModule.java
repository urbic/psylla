package coneforest.psi.core;
import coneforest.psi.*;

@Type("module")
public class PsiModule
	extends PsiDict
{

	protected void registerOperators(final PsiOperator... operators)
	{
		for(final PsiOperator oOperator: operators)
			put(oOperator.getName(), oOperator);
	}
}
