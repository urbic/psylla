package coneforest.psi;

public class PsiDictionary
	extends PsiObject
	implements PsiHashlike<PsiObject>
{
	public PsiDictionary()
	{
	}

	public PsiDictionary(PsiDictionary dict)
	{
		//this.dictionary=(java.util.HashMap<String, PsiObject>)dictionary.getDictionary().clone();
		this.dictionary=(java.util.HashMap<String, PsiObject>)dict.dictionary.clone();
	}

	/*
	private java.util.HashMap<String, PsiObject> getDictionary()
	{
		return dictionary;
	}
	*/

	@Override
	public String getTypeName() { return "dict"; }

	@Override
	public PsiDictionary psiClone()
	{
		return new PsiDictionary(this);
	}

	@Override
	public PsiObject psiGet(PsiAbstractStringlike key)
		throws PsiException
	{
		PsiObject result=dictionary.get(key.getString());
		if(result!=null)
			return result;
		else
			throw new PsiException("undefined");
	}

	@Override
	public void psiPut(PsiAbstractStringlike key, PsiObject obj)
	{
		dictionary.put(key.getString(), obj);
	}

	@Override
	public void psiUndef(PsiAbstractStringlike key)
	{
		dictionary.remove(key.getString());
	}

	@Override
	public PsiBoolean psiKnown(PsiAbstractStringlike key)
	{
		return new PsiBoolean(dictionary.containsKey(key.getString()));
	}

	public java.util.Iterator<java.util.Map.Entry<String, PsiObject>> iterator()
	{
		return dictionary.entrySet().iterator();
	}

	@Override
	public int length()
	{
		return dictionary.size();
	}

	@Override
	public PsiInteger psiLength()
	{
		return new PsiInteger(length());
	}

	@Override
	public boolean isEmpty()
	{
		return dictionary.isEmpty();
	}

	@Override
	public PsiBoolean psiIsEmpty()
	{
		return new PsiBoolean(isEmpty());
	}

	public String toString()
	{
		StringBuilder sb=new StringBuilder("<");
		if(dictionary.size()>0)
		{
			//for(java.util.Map.Entry<String, PsiObject> entry: entrySet())
			for(java.util.Map.Entry<String, PsiObject> entry: this)
				sb.append("/"+entry.getKey()+" "+entry.getValue()+" ");
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append(">");
		return sb.toString();
	}

	private java.util.HashMap<String, PsiObject> dictionary
		=new java.util.HashMap<String, PsiObject>();
}
