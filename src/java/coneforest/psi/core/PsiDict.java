package coneforest.psi.core;

/**
*	A representation of Ψ-{@code dict}, a dictionary.
*/
public class PsiDict
	implements PsiDictlike<PsiObject>
{
	public PsiDict()
	{
		this.dict=new java.util.HashMap<String, PsiObject>();
	}

	public PsiDict(final PsiDict oDict)
	{
		this.dict=(java.util.HashMap<String, PsiObject>)oDict.dict.clone();
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
		return new PsiDict(this);
	}

	@Override
	public PsiObject get(final String key)
		throws PsiException
	{
		final PsiObject result=dict.get(key);
		if(result!=null)
			return result;
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
