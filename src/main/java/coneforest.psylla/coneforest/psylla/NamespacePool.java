package coneforest.psylla;

import coneforest.psylla.core.*;
import java.util.HashMap;

public class NamespacePool
{
	public PsyNamespace get(final String prefix)
	{
		if(!pool.containsKey(prefix))
			pool.put(prefix, new PsyNamespace(prefix));
		return pool.get(prefix);
	}

	private final HashMap<String, PsyNamespace> pool=new HashMap<>();
}
