package coneforest.psylla;

import coneforest.psylla.core.types.PsyObject;
import coneforest.psylla.core.errors.PsyUndefined;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TypeResolver
{
	public static Class<? extends PsyObject> resolve(final String typeName)
		throws PsyUndefined
	{
		try
		{
			return (Class<? extends PsyObject>)Class.forName(
					(new BufferedReader(new InputStreamReader(
							TypeResolver.class.getClassLoader().getResourceAsStream(
									"META-INF/psylla/type/"+typeName)))).readLine());
			// TODO: introduce caching
		}
		catch(final IOException|ClassNotFoundException ex)
		{
			// TODO more appropriate exception when IOException is thrown
			throw new PsyUndefined();
		}
	}
}
