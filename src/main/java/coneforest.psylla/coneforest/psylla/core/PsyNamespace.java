package coneforest.psylla.core;

import coneforest.psylla.runtime.*;
import java.util.ArrayList;

/**
*	The representation of {@code namespace}, a named dictionary.
*/
@Type("namespace")
public class PsyNamespace
	extends PsyDict
{
	/**
	*	The namespace prefix.
	*/
	private final String prefix;

	private ArrayList<PsyNamespace> imports=new ArrayList<>();

	public PsyNamespace(final String prefix)
	{
		this.prefix=prefix;
	}

	/**
	*	{@return the namespace prefix}
	*/
	public String prefix()
	{
		return prefix;
	}

	// TODO known()
	@Override
	public boolean known(final String key)
	{
		//System.out.println("namespace:known "+prefix+" "+key);
		if(super.known(key))
		//if(dict.containsKey(key))
			return true;
		for(final var oNamespace: imports)
		{
			if(oNamespace.known(key))
				return true;
		}
		return false;
	}

	@Override
	public PsyObject get(final String key)
		throws PsyUndefinedException
	{
		//System.out.println("namespace:get "+key);
		final var agenda=new Stack<PsyNamespace>();
		agenda.push(this);

		while(agenda.size()>0)
		{
			final var oNamespace=agenda.pop();
			final var o=oNamespace.dict.get(key);
			if(o!=null)
				return o;

			for(int i=oNamespace.imports.size()-1; i>=0; i--)
				agenda.push(oNamespace.imports.get(i));
		}
		throw new PsyUndefinedException();
	}

	public void psyImport(final PsyNamespace oNamespace)
	{
		imports.add(0, oNamespace);
	}

	@Override
	public String toSyntaxString()
	{
		return "%namespace="+prefix+"%";
	}
}
