package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of Ψ-{@code namespace}, a named dictionary.
*/
@coneforest.psylla.Type("namespace")
public class PsyNamespace
	extends PsyDict
{

	private PsyNamespace(final String prefix)
	{
		this.prefix=prefix;
	}

	/**
	*	Returns a Ψ-{@code namespace} with the given Ψ-{@code stringy} prefix.
	*
	*	@param oPrefix the given prefix.
	*	@return a namespace.
	*/
	public static PsyNamespace psyNamespace(final PsyStringy oPrefix)
	{
		return forName(oPrefix.stringValue());
	}

	public static PsyNamespace forName(final String prefix)
	{
		if(pool.containsKey(prefix))
			return pool.get(prefix);
		final PsyNamespace oNamespace=new PsyNamespace(prefix);
		pool.put(prefix, oNamespace);
		return oNamespace;
	}

	protected void registerOperators(final PsyOperator... operators)
	{
		for(final PsyOperator oOperator: operators)
			put(oOperator.getName(), oOperator);
	}

	/**
	*	Returns the namespace prefix.
	*
	*	@return the prefix.
	*/
	public String prefix()
	{
		return prefix;
	}

	// TODO known()

	/*
	@Override
	public PsyObject get(final String key)
		throws PsyException
	{
		final Stack<PsyNamespace> agenda=new Stack<PsyNamespace>();
		agenda.push(this);

		while(agenda.size()>0)
		{
			final PsyNamespace oNamespace=agenda.pop();
			if(((PsyDict)oNamespace).known(key))
				return ((PsyDict)oNamespace).get(key);
			for(int i=oNamespace.parents.size()-1; i>=0; i--)
				agenda.push(oNamespace.parents.get(i));
		}
		throw new PsyUndefinedException();
	}
	*/

	@Override
	public PsyObject get(final String key)
		throws PsyException
	{
		if(super.known(key))
			return super.get(key);
		for(PsyNamespace oNameSpace: parents)
			if(oNameSpace.known(key))
				return oNameSpace.get(key);
		throw new PsyUndefinedException();
	}

	@Override
	public String toSyntaxString()
	{
		return "|namespace="+prefix+"|";
	}

	public static PsyNamespace getNamespace(final String prefix)
		throws PsyException
	{
		if(pool.containsKey(prefix))
			return pool.get(prefix);
		throw new PsyUndefinedException();	// TODO
	}

	private final String prefix;

	private java.util.ArrayList<PsyNamespace> parents
		=new java.util.ArrayList<PsyNamespace>();

	private static final java.util.HashMap<String, PsyNamespace> pool
			=new java.util.HashMap<String, PsyNamespace>();
}
