package coneforest.psi;

abstract public class PsiAbstractDictionary<V extends PsiObject>
	extends PsiAbstractObject
	implements PsiDictionarylike<V>
{
	@Override
	public String getTypeName()
	{
		return "dict";
	}

	@Override
	public V psiGet(PsiStringlike key)
		throws PsiException
	{
		return psiGet(key.getString());
	}

	@Override
	public void psiPut(PsiStringlike key, V obj)
	{
		psiPut(key.getString(), obj);
	}

	@Override
	abstract public boolean known(String keyString);

	@Override
	public PsiBoolean psiKnown(PsiStringlike key)
	{
		return psiKnown(key.getString());
	}

	@Override
	abstract public void psiUndef(String keyString);

	@Override
	public void psiUndef(PsiStringlike key)
	{
		psiUndef(key.getString());
	}

	@Override
	public V psiDelete(PsiStringlike key)
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
			psiUndef(entry.getKey());
	}

	@Override
	public String toString()
	{
		return toStringHelper(this);
	}

	@Override
	public String toStringHelper(PsiLengthy lengthy)
	{
		StringBuilder sb=new StringBuilder("<");
		if(length()>0)
		{
			for(java.util.Map.Entry<String, ? extends PsiObject> entry: this)
			{
				sb.append('/');
				sb.append(entry.getKey());
				sb.append(' ');
				PsiObject obj=entry.getValue();
				if(obj instanceof PsiLengthy)
					sb.append(obj==lengthy? "-"+obj.getTypeName()+"-": ((PsiLengthy)obj).toStringHelper(this));
				else
					sb.append(obj.toString());
				sb.append(' ');
			}
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append('>');
		return sb.toString();
	}
}
