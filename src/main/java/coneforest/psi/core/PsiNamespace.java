package coneforest.psi.core;
import coneforest.psi.*;

/**
*	A representation of Ψ-{@code namespace}, a named dictionary.
*/
@coneforest.psi.Type("namespace")
public class PsiNamespace
	extends PsiDict
{

	private PsiNamespace(final String prefix)
	{
		this.prefix=prefix;
	}

	/**
	*	Returns a Ψ-{@code namespace} with the given Ψ-{@code stringy} prefix.
	*
	*	@param oPrefix the given prefix.
	*	@return a namespace.
	*/
	public static PsiNamespace psiNamespace(final PsiStringy oPrefix)
	{
		return forName(oPrefix.stringValue());
	}

	public static PsiNamespace forName(final String prefix)
	{
		if(pool.containsKey(prefix))
			return pool.get(prefix);
		final PsiNamespace oNamespace=new PsiNamespace(prefix);
		pool.put(prefix, oNamespace);
		return oNamespace;
	}

	/**
	*	Returns the namespace prefix.
	*
	*	@param oPrefix the given prefix.
	*/
	public String prefix()
	{
		return prefix;
	}

	// TODO known()

	/*
	@Override
	public PsiObject get(final String key)
		throws PsiException
	{
		final Stack<PsiNamespace> agenda=new Stack<PsiNamespace>();
		agenda.push(this);

		while(agenda.size()>0)
		{
			final PsiNamespace oNamespace=agenda.pop();
			if(((PsiDict)oNamespace).known(key))
				return ((PsiDict)oNamespace).get(key);
			for(int i=oNamespace.parents.size()-1; i>=0; i--)
				agenda.push(oNamespace.parents.get(i));
		}
		throw new PsiUndefinedException();
	}
	*/

	@Override
	public PsiObject get(final String key)
		throws PsiException
	{
		if(super.known(key))
			return super.get(key);
		for(PsiNamespace oNameSpace: parents)
			if(oNameSpace.known(key))
				return oNameSpace.get(key);
		throw new PsiUndefinedException();
	}

	@Override
	public String toSyntaxString()
	{
		return "|namespace="+prefix+"|";
	}

	public static PsiNamespace getNamespace(final String prefix)
		throws PsiException
	{
		if(pool.containsKey(prefix))
			return pool.get(prefix);
		throw new PsiUndefinedException();	// TODO
	}

	private final String prefix;

	private java.util.ArrayList<PsiNamespace> parents
		=new java.util.ArrayList<PsiNamespace>();

	private static final java.util.HashMap<String, PsiNamespace> pool
			=new java.util.HashMap<String, PsiNamespace>();
}
