package coneforest.psi;

/**
*	A representation of Ψ-{@code dictlike}, an abstraction of a dictionary.
*
*	@param <V> a type of contained values.
*/
public interface PsiDictlike<V extends PsiObject>
	extends
		PsiClearable,
		PsiContainer<PsiObject>,
		PsiIndexed<PsiStringy, V>
{
	/**
	 *	@return a string {@code "dictlike"}.
	 */
	@Override
	default public String getTypeName()
	{
		return "dictlike";
	}

	public V get(String key)
		throws PsiException;

	@Override
	default public V psiGet(PsiStringy oKey)
		throws PsiException
	{
		return get(oKey.stringValue());
	}

	@Override
	default public PsiArraylike<V> psiGetAll(PsiIterable<PsiStringy> oEnumeration)
		throws PsiException
	{
		PsiArraylike<V> oResult=(PsiArraylike<V>)new PsiArray();
		for(PsiStringy oKey: oEnumeration)
			oResult.psiAppend(psiGet(oKey));
		return oResult;
	}

	public void put(String key, V oValue);

	@Override
	default public void psiPut(PsiStringy oKey, V oValue)
	{
		put(oKey.stringValue(), oValue);
	}

	public boolean known(String keyString);

	@Override
	default public PsiBoolean psiKnown(PsiStringy oKey)
	{
		return PsiBoolean.valueOf(known(oKey.stringValue()));
	}

	public void undef(String key);

	/**
	 *	Deletes a key and associated value from this dictionary.
	 *
	 *	@param oKey a Ψ-{@code stringy} key.
	 */
	default public void psiUndef(PsiStringy oKey)
	{
		undef(oKey.stringValue());
	}

	@Override
	public PsiIterable<PsiStringy> psiKeys();

	@Override
	default public PsiIterable<V> psiValues()
	{
		return new PsiIterable<V>()
			{
				@Override
				public java.util.Iterator<V> iterator()
				{
					return new java.util.Iterator<V>()
						{
							@Override
							public boolean hasNext()
							{
								return parentIterator.hasNext();
							}

							@Override
							public V next()
							{
								try
								{
									return psiGet(parentIterator.next());
								}
								catch(PsiException e)
								{
									throw new AssertionError(e);
								}
							}

							private java.util.Iterator<PsiStringy> parentIterator
								=psiKeys().iterator();
						};
				}
			};
	}

	@Override
	default public void psiDelete(final PsiStringy oKey)
		throws PsiException
	{
		psiUndef(oKey);
	}

	@Override
	default public V psiExtract(final PsiStringy oKey)
		throws PsiException
	{
		V oResult=psiGet(oKey);
		psiUndef(oKey);
		return oResult;
	}

	@Override
	public PsiDictlike<V> psiSlice(final PsiIterable<PsiStringy> oEnumeration)
		throws PsiException;

	@Override
	default public String toSyntaxString()
	{
		return "<"+toSyntaxStringHelper(this)+">";
	}

	@Override
	default public PsiIterable<PsiObject> psiEntries()
	{
		return new PsiIterable<PsiObject>()
			{
				@Override
				public java.util.Iterator<PsiObject> iterator()
				{
					return new java.util.Iterator<PsiObject>()
						{
							@Override
							public boolean hasNext()
							{
								return parentIterator.hasNext() || flag;
							}

							@Override
							public PsiObject next()
							{
								try
								{
									return (flag=!flag)?
										(key=(PsiName)parentIterator.next()): psiGet(key);
								}
								catch(PsiException e)
								{
									throw new AssertionError(e);
								}
							}

							private boolean flag=false;

							private PsiName key;

							private java.util.Iterator<PsiStringy> parentIterator
								=psiKeys().iterator();

						};
				}
			};
	}

	//@Override
	default public String toSyntaxStringHelper(PsiLengthy lengthy)
	{
		StringBuilder sb=new StringBuilder();
		if(length()>0)
		{
			for(PsiObject obj: this.psiEntries())
			{
				if(obj instanceof PsiLengthy)
					sb.append(obj==lengthy? "-"+obj.getTypeName()+"-": ((PsiLengthy)obj).toSyntaxString());
				else
					sb.append(obj.toSyntaxString());
				sb.append(' ');
			}
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
	}
	
	@Override
	default public void psiClear()
	{
		for(PsiStringy key: this.psiKeys())
			psiUndef(key);
	}
}
