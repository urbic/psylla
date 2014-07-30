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
	public PsiObject psiGet(PsiStringlike key)
		throws PsiException
	{
		PsiObject result=dictionary.get(key.getValue());
		if(result!=null)
			return result;
		else
			throw new PsiException("undefined");
	}

	@Override
	public void psiPut(PsiStringlike key, PsiObject obj)
	{
		dictionary.put(key.getValue(), obj);
	}

	@Override
	public void psiUndef(PsiStringlike key)
	{
		dictionary.remove(key.getValue());
	}

	@Override
	public PsiBoolean psiKnown(PsiStringlike key)
	{
		return new PsiBoolean(dictionary.containsKey(key.getValue()));
	}

	public java.util.Iterator<java.util.Map.Entry<String, PsiObject>> iterator()
	{
		return dictionary.entrySet().iterator();
	}

	@Override
	public PsiInteger psiLength()
	{
		return new PsiInteger(dictionary.size());
	}

	@Override
	public PsiBoolean psiIsEmpty()
	{
		return new PsiBoolean(dictionary.isEmpty());
	}

	public String toString()
	{
		StringBuilder sb=new StringBuilder("<<");
		if(dictionary.size()>0)
		{
			//for(java.util.Map.Entry<String, PsiObject> entry: entrySet())
			for(java.util.Map.Entry<String, PsiObject> entry: this)
				sb.append("/"+entry.getKey()+" "+entry.getValue()+" ");
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append(">>");
		return sb.toString();
	}

	private java.util.HashMap<String, PsiObject> dictionary
		=new java.util.HashMap<String, PsiObject>();
}
