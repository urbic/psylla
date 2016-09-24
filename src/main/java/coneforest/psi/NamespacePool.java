package coneforest.psi;
import coneforest.psi.core.*;

public class NameSpacePool
{
	/*
	public String currentPrefix()
	{
		return currentPrefix;
	}

	public void setCurrent(final String prefix)
	{
		currentPrefix=prefix;
		if(!(prefix.equals("") || pool.containsKey(prefix)))
			pool.put(currentPrefix, new PsiNameSpace(prefix));
	}

	private String currentPrefix="";
	*/

	public PsiNameSpace forPrefix(final String prefix)
	{
		if(pool.containsKey(prefix))
			return pool.get(prefix);
		final PsiNameSpace oNameSpace=new PsiNameSpace(prefix.intern());
		pool.put(prefix, oNameSpace);
		return oNameSpace;
	}

	private final java.util.HashMap<String, PsiNameSpace> pool
		=new java.util.HashMap<String, PsiNameSpace>();
}
