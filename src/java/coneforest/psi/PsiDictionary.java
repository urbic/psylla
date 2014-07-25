package coneforest.psi;

public class PsiDictionary
	extends PsiObject
	implements PsiHashlike<PsiObject>
{
	public String getTypeName() { return "dict"; }

	public PsiObject get(String key)
		throws PsiException
	{
		PsiObject result=dictionary.get(key);
		if(result!=null)
			return result;
		else
			throw new PsiException("undefined");
	}
	
	public PsiObject get(PsiStringlike oKey)
		throws PsiException
	{
		return get(oKey.getValue());
	}
	
	public void put(String key, PsiObject obj)
	{
		dictionary.put(key, obj);
	}

	public void put(PsiStringlike oKey, PsiObject obj)
	{
		put(oKey.getValue(), obj);
	}

	public void undef(String key)
	{
		dictionary.remove(key);
	}

	public void undef(PsiStringlike oKey)
	{
		undef(oKey.getValue());
	}

	public PsiBoolean known(PsiStringlike key)
	{
		return new PsiBoolean(containsKey(key));
	}

	public java.util.Iterator<java.util.Map.Entry<String, PsiObject>> iterator()
	{
		return dictionary.entrySet().iterator();
	}

	public boolean containsKey(PsiStringlike key)
	{
		return dictionary.containsKey(key.getValue());
	}

	public int length()
	{
		return dictionary.size();
	}

	public String toString()
	{
		StringBuilder sb=new StringBuilder("<<");
		if(length()>0)
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
