package coneforest.psi;

public class PsiDictionary extends PsiObject
{
	public String getTypeName() { return "dict"; }

	public void put(PsiStringlike key, PsiObject obj)
	{
		put(key.getValue(), obj);
	}

	public void put(String name, PsiObject obj)
	{
		dictionary.put(name, obj);
	}

	public java.util.Set<java.util.Map.Entry<String, PsiObject>> entrySet()
	{
		return dictionary.entrySet();
	}

	public PsiObject get(PsiStringlike key)
	{
		return get(key.getValue());
	}
	
	public PsiObject get(String key)
	{
		return dictionary.get(key);
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
