package coneforest.psylla;

import coneforest.psylla.core.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
*	The type resolver.
*/
public interface TypeResolver
{
	/**
	*	Resolves the type name
	*/
	public static Class<? extends PsyObject> resolve(final String typeName)
		throws PsyUndefinedException
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
			throw new PsyUndefinedException();
		}
	}
}
