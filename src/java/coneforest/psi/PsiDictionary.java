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
	
	public PsiObject get(PsiStringlike key)
		throws PsiException
	{
		return get(key.getValue());
	}
	
	public void put(String name, PsiObject obj)
	{
		dictionary.put(name, obj);
	}

	public void put(PsiStringlike key, PsiObject obj)
	{
		put(key.getValue(), obj);
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
