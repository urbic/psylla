package coneforest.psi;
import coneforest.psi.core.*;

/**
*	A namespace pool.
*/
public class NamespacePool
{
	public NamespacePool()
	{
		// Store default namespace
		pool.put("", new PsiNamespace(""));
	}

	public PsiNamespace obtain(final String prefix)
	{
		if(pool.containsKey(prefix))
			return get(prefix);
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
