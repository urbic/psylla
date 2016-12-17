package coneforest.psi;
import coneforest.psi.core.*;

public class NamespacePool
{
	public NamespacePool()
	{
		// Store default namespace
		pool.put("", new PsiNamespace(""));
	}

	public PsiNamespace allocate(final String prefix)
	{
		if(pool.containsKey(prefix))
			return pool.get(prefix);
		final PsiNamespace oNamespace=new PsiNamespace(prefix.intern());
		pool.put(prefix, oNamespace);
		return oNamespace;
	}

	public PsiNamespace get(final String prefix)
	{
		return pool.get(prefix);
	}

	private final java.util.HashMap<String, PsiNamespace> pool
		=new java.util.HashMap<String, PsiNamespace>();
}
