package coneforest.psi;
import coneforest.psi.core.*;

public class NamespacePool
{
	public NamespacePool()
	{
		// Store default namespace
		pool.put("", new PsiNamespace(""));
	}

	public PsiNamespace forPrefix(final String prefix)
	{
		if(pool.containsKey(prefix))
			return pool.get(prefix);
		final PsiNamespace oNamespace=new PsiNamespace(prefix.intern());
		pool.put(prefix, oNamespace);
		return oNamespace;
	}

	private final java.util.HashMap<String, PsiNamespace> pool
		=new java.util.HashMap<String, PsiNamespace>();
}
