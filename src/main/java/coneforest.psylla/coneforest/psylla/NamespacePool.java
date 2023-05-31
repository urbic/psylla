package coneforest.psylla;
import coneforest.psylla.core.*;

public class NamespacePool
{
	public PsyNamespace get(final String prefix)
	{
		if(!pool.containsKey(prefix))
			pool.put(prefix, new PsyNamespace(prefix));
		return pool.get(prefix);
	}

	private final java.util.HashMap<String, PsyNamespace> pool
		=new java.util.HashMap<String, PsyNamespace>();
}
