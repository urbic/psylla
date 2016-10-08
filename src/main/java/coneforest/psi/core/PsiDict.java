package coneforest.psi.core;

/**
*	A representation of Ψ-{@code dict}, a dictionary.
*/
public class PsiDict
	implements PsiDictlike<PsiObject>
{
	/**
	*	Creates a new empty Ψ-{@code dict}.
	*/
	public PsiDict()
	{
		this(new java.util.HashMap<String, PsiObject>());
	}

	/**
	*	Creates a new Ψ-{@code dict} wrapped around the given hash map.
	*
	*	@param dict a given hash map.
	*/
	public PsiDict(final java.util.HashMap<String, PsiObject> dict)
	{
		this.dict=dict;
	}

	/**
	*	@return a string {@code "dict"}.
	*/
	@Override
	public String typeName()
	{
		return "dict";
	}

	@Override
	public PsiDict psiClone()
	{
		return new PsiDict((java.util.HashMap<String, PsiObject>)dict.clone());
	}

	@Override
	public PsiObject get(final String key)
		throws PsiException
	{
		final PsiObject oValue=dict.get(key);
		if(oValue!=null)
			return oValue;
		else
			throw new PsiUndefinedException();
	}

	@Override
	public void put(final String key, final PsiObject o)
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
	public java.util.Iterator<PsiObject> iterator()
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
	public void psiClear()
	{
		dict.clear();
	}

	@Override
	public PsiDict psiSlice(final PsiIterable<PsiStringy> oKeys)
		throws PsiException
	{
		final PsiDict values=new PsiDict();
		for(PsiStringy oKey: oKeys)
			values.psiPut(oKey, psiGet(oKey));
		return values;
	}

	@Override
	public PsiIterable<PsiStringy> psiKeys()
	{
		return new PsiIterable<PsiStringy>()
			{
				@Override
				public java.util.Iterator<PsiStringy> iterator()
				{
					return new java.util.Iterator<PsiStringy>()
						{
							@Override
							public boolean hasNext()
							{
								return parentIterator.hasNext();
							}

							@Override
							public PsiStringy next()
							{
								return new PsiName(parentIterator.next());
							}

							private final java.util.Iterator<String> parentIterator
								=dict.keySet().iterator();
						};
				}
			};
	}

	private final java.util.HashMap<String, PsiObject> dict;
}
