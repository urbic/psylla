package coneforest.psi;

public class PsiModule
	extends PsiDictionary
{
	protected void register(Class<? extends PsiOperator> operatorClass)
	{
		try
		{
			PsiOperator operator=operatorClass.newInstance();
			String operatorName=operator.getName();
			//System.out.println("Registering ["+operatorName+"]");
			put(operatorName, operator);
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
