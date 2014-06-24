package coneforest.psi;

public class DictionaryStack extends java.util.Stack<PSIDictionary>
{
	public PSIObject load(PSIStringlike key)
	{
		for(int i=size()-1; i>=0; i--)
		{
			if(elementAt(i).containsKey(key))
				return elementAt(i).get(key);
		}
		return null;
	}

	public PSIDictionary getCurrentDictionary()
	{
		return peek();
	}
	
	public PSIDictionary getSystemDictionary()
	{
		return elementAt(0);
	}
}
