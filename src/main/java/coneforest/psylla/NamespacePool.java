package coneforest.psylla;
import coneforest.psylla.core.*;

public class NamespacePool
{
	public PsyNamespace namespace(final String prefix)
	{
		if(pool.containsKey(prefix))
			return pool.get(prefix);
		final var oNamespace=new PsyNamespace(prefix);
		pool.put(prefix, oNamespace);
		return oNamespace;
	}
	public PsyNamespace psyNamespace(final PsyStringy oPrefix)
	{
		return namespace(oPrefix.stringValue());
	}

	private java.util.HashMap<String, PsyNamespace> pool
		=new java.util.HashMap<String, PsyNamespace>();
}
