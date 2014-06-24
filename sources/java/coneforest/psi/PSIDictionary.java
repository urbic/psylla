package coneforest.psi;

public class PSIDictionary extends PSIObject
{
	public String getTypeName() { return "dict"; }

	public void put(PSIStringlike key, PSIObject obj)
	{
		put(key.getValue(), obj);
	}

	public void put(String name, PSIObject obj)
	{
		dictionary.put(name, obj);
	}

	public PSIObject get(PSIStringlike key)
	{
		return get(key.getValue());
	}
	
	public PSIObject get(String key)
	{
		return dictionary.get(key);
	}
	
	public boolean containsKey(PSIStringlike key)
	{
		return dictionary.containsKey(key.getValue());
	}

	public int size()
	{
		return dictionary.size();
	}

	public void execute(PSIInterpreter interpreter)
	{
		// TODO
	}

	public String toString()
	{
		return "-dict-";
	}

	private java.util.HashMap<String, PSIObject> dictionary
		=new java.util.HashMap<String, PSIObject>();
}
