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
		PsiIterable<java.util.Map.Entry<String, V>>,
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

	default public PsiSetlike<PsiObject> psiKeys()
	{
		PsiSet set=new PsiSet();
		for(java.util.Map.Entry<String, V> entry: this)
			set.psiAppend(new PsiName(entry.getKey()));
		return set;
	}

	// TODO return Set?
	default public PsiArray psiValues()
	{
		PsiArray values=new PsiArray();
		for(java.util.Map.Entry<String, V> entry: this)
			values.psiAppend(entry.getValue());
		return values;
	}

	@Override
	default public V psiExtract(PsiStringy key)
		throws PsiException
	{
		V result=psiGet(key);
		psiUndef(key);
		return result;
	}

	@Override
	public PsiDictlike<V> psiSlice(PsiIterable<PsiStringy> keys)
		throws PsiException;

	@Override
	default public String toSyntaxString()
	{
		return "<"+toSyntaxStringHelper(this)+">";
	}

	//@Override
	default public String toSyntaxStringHelper(PsiLengthy lengthy)
	{
		StringBuilder sb=new StringBuilder();
		if(length()>0)
		{
			for(java.util.Map.Entry<String, ? extends PsiObject> entry: this)
			{
				sb.append('/');
				sb.append(entry.getKey());
				sb.append(' ');
				PsiObject obj=entry.getValue();
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
		for(java.util.Map.Entry<String, V> entry: this)
			undef(entry.getKey());
	}
}
