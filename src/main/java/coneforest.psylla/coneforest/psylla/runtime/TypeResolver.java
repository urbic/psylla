package coneforest.psylla.runtime;

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
	*	Resolves the type name.
	*
	*	@param typeName the type name to resolve.
	*	@return the class that was found.
	*	@throws PsyUndefinedException TODO
	*/
	public static Class<? extends PsyObject> resolve(final String typeName)
		throws PsyUndefinedException
	{
		try
		{
			try(final var br=new BufferedReader(new InputStreamReader(
					// TODO: use DynamicClassLoader
					TypeResolver.class.getClassLoader().getResourceAsStream(
							"META-INF/psylla/type/"+typeName))))
			{
				return Class.forName(br.readLine()).asSubclass(PsyObject.class);
			}
			// TODO: introduce caching
		}
		catch(final IOException|ClassNotFoundException ex)
		{
			// TODO: more appropriate exception when IOException is thrown
			throw new PsyUndefinedException();
		}
	}
}
