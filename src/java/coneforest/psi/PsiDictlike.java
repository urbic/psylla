package coneforest.psi;

/**
 *	A type of dictionary-like objects.
 *
 *	@param <V> a type of contained values.
 */
public interface PsiDictlike<V extends PsiObject>
	extends
		PsiLengthy,
		PsiIndexed<PsiStringy, V>,
		//PsiIterable<java.util.Map.Entry<String, V>>,
		PsiIterable<PsiObject>,
		PsiClearable
{
	@Override
	default public String getTypeName()
	{
		return "dictlike";
	}

	public V get(String key)
		throws PsiException;

	@Override
	default public V psiGet(PsiStringy key)
		throws PsiException
	{
		return get(key.getString());
	}

	@Override
	default public PsiArraylike<V> psiGetAll(PsiIterable<PsiStringy> iterable)
		throws PsiException
	{
		PsiArraylike<V> result=(PsiArraylike<V>)new PsiArray();
		for(PsiStringy key: iterable)
			result.psiAppend(psiGet(key));
		return result;
	}

	public void put(String key, V value);

	@Override
	default public void psiPut(PsiStringy key, V obj)
	{
		put(key.getString(), obj);
	}

	public boolean known(String keyString);

	@Override
	default public PsiBoolean psiKnown(PsiStringy key)
	{
		return PsiBoolean.valueOf(known(key.getString()));
	}

	public void undef(String keyString);

	/**
	 *	Delete a key and a value associated with it from this object.
	 *
	 *	@param key a key. 
	 */
	default public void psiUndef(PsiStringy key)
	{
		undef(key.getString());
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
	default public void psiDelete(final PsiStringy key)
		throws PsiException
	{
		psiUndef(key);
	}

	@Override
	default public V psiExtract(final PsiStringy key)
		throws PsiException
	{
		V result=psiGet(key);
		psiUndef(key);
		return result;
	}

	@Override
	public PsiDictlike<V> psiSlice(final PsiIterable<PsiStringy> keys)
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
