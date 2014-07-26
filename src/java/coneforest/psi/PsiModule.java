package coneforest.psi;

public class PsiModule
	extends PsiDictionary
{
	protected void registerOperatorClasses(Class<? extends PsiOperator>[] operatorClasses)
	{
		try
		{
			for(Class<? extends PsiOperator> operatorClass: operatorClasses)
			{
				PsiOperator operator=operatorClass.newInstance();
				String operatorName=operator.getName();
				put(operatorName, operator);
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

	/*
	public PsiObject getByName(String name)
	{
		return get(new PsiName(name));
	}

	public void putByName(String name, PsiObject obj)
	{
		put(new PsiName(name), obj);
	}
	*/
}
