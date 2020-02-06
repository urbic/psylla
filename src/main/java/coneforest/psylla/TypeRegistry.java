package coneforest.psylla;

public class TypeRegistry
{
	public static void put(final String typeName, final Class clazz)
	{
		System.out.println(typeName+" registered");
		registry.put(typeName, clazz);
	}

	public static Class get(final String typeName)
	{
		return registry.get(typeName);
	}

	private static java.util.HashMap<String, Class> registry
		=new java.util.HashMap<String, Class>();
}
