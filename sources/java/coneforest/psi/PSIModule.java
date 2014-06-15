package coneforest.psi;

public class PSIModule extends PSIDictionary
{
	protected void register(Class<? extends PSIOperator> operatorClass)
	{
		try
		{
			PSIOperator operator=operatorClass.newInstance();
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
	public PSIObject getByName(String name)
	{
		return get(new PSIName(name));
	}

	public void putByName(String name, PSIObject obj)
	{
		put(new PSIName(name), obj);
	}
	*/
}
