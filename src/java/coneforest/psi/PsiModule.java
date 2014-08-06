package coneforest.psi;

public class PsiModule
	extends PsiDictionary
{
	protected void registerOperatorClasses(Class<? extends PsiOperator>... operatorClasses)
	{
		try
		{
			for(Class<? extends PsiOperator> operatorClass: operatorClasses)
			{
				PsiOperator operator=operatorClass.newInstance();
				psiPut(operator.getName(), operator);
			}
		}
		catch(InstantiationException e)
		{
			// TODO
			System.out.println("INSTANTIATION EXCEPTION");
		}
		catch(IllegalAccessException e)
		{
			// TODO
			System.out.println("ILLEGAL ACCESS EXCEPTION");
		}
	}
}
