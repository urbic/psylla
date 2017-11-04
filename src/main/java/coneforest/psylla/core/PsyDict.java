package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of Ψ-{@code dict}, a dictionary.
*/
@Type("dict")
public class PsyDict
	implements PsyDictlike<PsyObject>
{
	/**
	*	Creates a new empty Ψ-{@code dict}.
	*/
	public PsyDict()
	{
		this(new java.util.HashMap<String, PsyObject>());
	}

	/**
	*	Creates a new Ψ-{@code dict} wrapped around the given hash map.
	*
	*	@param dict a given hash map.
	*/
	public PsyDict(final java.util.HashMap<String, PsyObject> dict)
	{
		this.dict=dict;
	}

	@Override
	public PsyDict psyClone()
	{
		return new PsyDict((java.util.HashMap<String, PsyObject>)dict.clone());
	}

	@Override
	public PsyObject get(final String key)
		throws PsyException
	{
		final PsyObject oValue=dict.get(key);
		if(oValue!=null)
			return oValue;
		else
			throw new PsyUndefinedException();
	}

	@Override
	public void put(final String key, final PsyObject o)
	{
		dict.put(key, o);
	}

	@Override
	public void undef(final String key)
	{
		dict.remove(key);
	}

	@Override
	public boolean known(final String key)
	{
		return dict.containsKey(key);
	}

	@Override
	public java.util.Iterator<PsyObject> iterator()
	{
		return dict.values().iterator();
	}

	@Override
	public int length()
	{
		return dict.size();
	}

	@Override
	public boolean isEmpty()
	{
		return dict.isEmpty();
	}

	@Override
	public void psyClear()
	{
		dict.clear();
	}

	@Override
	public PsyDict psySlice(final PsyIterable<PsyStringy> oKeys)
		throws PsyException
	{
		final PsyDict values=new PsyDict();
		for(PsyStringy oKey: oKeys)
			values.psyPut(oKey, psyGet(oKey));
		return values;
	}

	@Override
	public PsyIterable<PsyStringy> psyKeys()
	{
		return new PsyIterable<PsyStringy>()
			{
				@Override
				public java.util.Iterator<PsyStringy> iterator()
				{
					return new java.util.Iterator<PsyStringy>()
						{
							@Override
							public boolean hasNext()
							{
								return parentIterator.hasNext();
							}

							@Override
							public PsyStringy next()
							{
								return new PsyName(parentIterator.next());
							}

							private final java.util.Iterator<String> parentIterator
								=dict.keySet().iterator();
						};
				}
			};
	}

	private final java.util.HashMap<String, PsyObject> dict;
}
