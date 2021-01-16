package coneforest.psylla;

import coneforest.psylla.core.*;

public class TypeResolver
{
	public static Class<? extends PsyObject> resolve(final String typeName)
		throws PsyUndefinedException
	{
		try
		{
			return (Class<? extends PsyObject>)Class.forName(
					(new java.io.BufferedReader(new java.io.InputStreamReader(
							TypeResolver.class.getClassLoader().getResourceAsStream(
									"META-INF/psylla/types/"+typeName)))).readLine());
		}
		catch(final java.io.IOException|ClassNotFoundException e)
		{
			// TODO more appropriate exception when IOException is thrown
			throw new PsyUndefinedException();
		}
	}
}
