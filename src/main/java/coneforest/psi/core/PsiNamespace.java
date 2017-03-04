package coneforest.psi.core;
import coneforest.psi.*;

@coneforest.psi.Type("namespace")
public class PsiNamespace
	extends PsiDict
{
	public PsiNamespace(final String prefix)
	{
		this.prefix=prefix;
	}

	public PsiNamespace(final PsiStringy oPrefix)
	{
		this(oPrefix.stringValue());
	}

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

	private final String prefix;

	private java.util.ArrayList<PsiNamespace> parents
		=new java.util.ArrayList<PsiNamespace>();
}
