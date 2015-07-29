package coneforest.psi;

abstract public class PsiAbstractDict<V extends PsiObject>
	implements PsiDictlike<V>
{
	@Override
	public V psiGet(PsiStringlike key)
		throws PsiException
	{
		return get(key.getString());
	}

	@Override
	public PsiArraylike<V> psiGetAll(PsiIterable<PsiStringlike> iterable)
		throws PsiException
	{
		PsiArraylike<V> result=(PsiArraylike<V>)new PsiArray();
		for(PsiStringlike key: iterable)
			result.psiAppend(psiGet(key));
		return result;
	}

	@Override
	public void psiPut(PsiStringlike key, V obj)
	{
		put(key.getString(), obj);
	}

	//@Override
	//abstract public boolean known(String keyString);

	@Override
	public PsiBoolean psiKnown(PsiStringlike key)
	{
		return PsiBoolean.valueOf(known(key.getString()));
	}

	//@Override
	//abstract public void undef(String keyString);

	@Override
	public void psiUndef(PsiStringlike key)
	{
		undef(key.getString());
	}

	@Override
	public V psiExtract(PsiStringlike key)
		throws PsiException
	{
		V result=psiGet(key);
		psiUndef(key);
		return result;
	}

	@Override
	public PsiSet psiKeys()
	{
		PsiSet set=new PsiSet();
		for(java.util.Map.Entry<String, V> entry: this)
			set.psiAppend(new PsiName(entry.getKey()));
		return set;
	}

	@Override
	public PsiArray psiValues()
	{
		PsiArray values=new PsiArray();
		for(java.util.Map.Entry<String, V> entry: this)
			values.psiAppend(entry.getValue());
		return values;
	}

	@Override
	public void psiClear()
	{
		for(java.util.Map.Entry<String, V> entry: this)
			undef(entry.getKey());
	}

	@Override
	public String toString()
	{
		return "<"+toStringHelper(this)+">";
	}

	@Override
	public String toStringHelper(PsiLengthy lengthy)
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
					sb.append(obj==lengthy? "-"+obj.getTypeName()+"-": ((PsiLengthy)obj).toString());
				else
					sb.append(obj.toString());
				sb.append(' ');
			}
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
	}
}
