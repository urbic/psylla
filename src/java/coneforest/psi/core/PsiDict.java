package coneforest.psi.core;

/**
 *	A representation of Ψ-{@code dictionary} object.
 */
public class PsiDict
	implements PsiDictlike<PsiObject>
{
	@Override
	public String getTypeName()
	{
		return "dict";
	}

	public PsiDict()
	{
		this.dictionary=new java.util.HashMap<String, PsiObject>();
	}

	public PsiDict(final PsiDict oDict)
	{
		this.dictionary=(java.util.HashMap<String, PsiObject>)oDict.dictionary.clone();
	}

	@Override
	public PsiDict psiClone()
	{
		return new PsiDict(this);
	}

	@Override
	public PsiObject get(final String key)
		throws PsiException
	{
		final PsiObject result=dictionary.get(key);
		if(result!=null)
			return result;
		else
			throw new PsiUndefinedException();
	}

	@Override
	public void put(final String key, final PsiObject o)
	{
		dictionary.put(key, o);
	}

	@Override
	public void undef(final String key)
	{
		dictionary.remove(key);
	}

	@Override
	public boolean known(final String key)
	{
		return dictionary.containsKey(key);
	}

	/*
	@Override
	public java.util.Iterator<java.util.Map.Entry<String, PsiObject>> iterator()
	{
		return dictionary.entrySet().iterator();
	}
	*/

	@Override
	public java.util.Iterator<PsiObject> iterator()
	{
		return dictionary.values().iterator();
	}

	@Override
	public int length()
	{
		return dictionary.size();
	}

	@Override
	public boolean isEmpty()
	{
		return dictionary.isEmpty();
	}

	@Override
	public void psiClear()
	{
		dictionary.clear();
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
								=dictionary.keySet().iterator();
						};
				}
			};
	}

	private final java.util.HashMap<String, PsiObject> dictionary;
}
