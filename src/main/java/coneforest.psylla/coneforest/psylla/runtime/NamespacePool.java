package coneforest.psylla.runtime;

import coneforest.psylla.core.*;
import java.util.HashMap;

/**
*	A namespace pool.
*/
public class NamespacePool
{
	private final HashMap<String, PsyNamespace> pool=new HashMap<>();

	public NamespacePool()
	{
	}

	/**
	*	Allocates (if absent) and returns the {@code namespace} object with the given prefix.
	*
	*	@param prefix the prefix.
	*	@return the {@code namespace} object.
	*/
	public PsyNamespace get(final String prefix)
	{
		if(!pool.containsKey(prefix))
			pool.put(prefix, new PsyNamespace(prefix));
		return pool.get(prefix);
	}
}
