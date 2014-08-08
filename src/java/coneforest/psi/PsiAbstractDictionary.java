package coneforest.psi;

abstract public class PsiAbstractDictionary<V extends PsiObject>
	extends PsiObject
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
	public PsiSet psiKeys()
	{
		PsiSet set=new PsiSet();
		for(java.util.Map.Entry<String, V> entry: this)
			set.psiAppend(new PsiName(entry.getKey()));
		return set;
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
		return toString(this);
	}

	public String toString(PsiLengthy composite)
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
					sb.append(obj==composite? "-"+obj.getTypeName()+"-": ((PsiLengthy)obj).toString(this));
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
