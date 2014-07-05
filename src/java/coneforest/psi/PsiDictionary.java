package coneforest.psi;

public class PsiDictionary extends PsiObject
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

	public java.util.Set<java.util.Map.Entry<String, PsiObject>> entrySet()
	{
		return dictionary.entrySet();
	}

	public boolean containsKey(PsiStringlike key)
	{
		return dictionary.containsKey(key.getValue());
	}

	public int size()
	{
		return dictionary.size();
	}

	public String toString()
	{
		return "-dict-";
	}

	private java.util.HashMap<String, PsiObject> dictionary
		=new java.util.HashMap<String, PsiObject>();
}
