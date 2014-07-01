package coneforest.psi;

public class DictionaryStack extends java.util.Stack<PsiDictionary>
{
	public PsiObject load(PsiStringlike key)
	{
		for(int i=size()-1; i>=0; i--)
		{
			if(elementAt(i).containsKey(key))
				return elementAt(i).get(key);
		}
		return null;
	}

	public PsiDictionary getCurrentDictionary()
	{
		return peek();
	}
	
	public PsiDictionary getSystemDictionary()
	{
		return elementAt(0);
	}
}
