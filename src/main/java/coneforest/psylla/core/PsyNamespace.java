package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of Ψ-{@code namespace}, a named dictionary.
*/
@Type("namespace")
public class PsyNamespace
	extends PsyDict
{

	public PsyNamespace(final String prefix)
	{
		this.prefix=prefix;
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

	@Override
	public PsyObject get(final String key)
		throws PsyException
	{
		final Stack<PsyNamespace> agenda=new Stack<PsyNamespace>();
		agenda.push(this);

		while(agenda.size()>0)
		{
			final PsyNamespace oNamespace=agenda.pop();
			PsyObject o=oNamespace.dict.get(key);
			if(o!=null)
				return o;

			//for(int i=oNamespace.imports.size()-1; i>=0; i--)
			//	agenda.push(oNamespace.imports.get(i));
			imports.forEach(agenda::add);
		}
		throw new PsyUndefinedException();
	}

	public void psyImport(final PsyNamespace oNamespace)
	{
		imports.add(0, oNamespace);
	}

	/*
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
	*/

	@Override
	public String toSyntaxString()
	{
		return "|namespace="+prefix+"|";
	}

	private final String prefix;

	private java.util.ArrayList<PsyNamespace> imports
		=new java.util.ArrayList<PsyNamespace>();

}
