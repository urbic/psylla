package coneforest.psi;

public class TypeRegistry
{
	public static void put(String typeName, Class clazz)
	{
		registry.put(typeName, clazz);
	}

	public static Class get(String typeName)
	{
		return registry.get(typeName);
	}

	private static java.util.HashMap<String, Class> registry
		=new java.util.HashMap<String, Class>();
}
