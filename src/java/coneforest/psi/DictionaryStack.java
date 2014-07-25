package coneforest.psi;

public class DictionaryStack extends Stack<PsiDictionary>
{
	public PsiObject load(PsiStringlike key)
		throws PsiException
	{
		return where(key).get(key);
	}

	public PsiDictionary where(PsiStringlike key)
	{
		for(int i=size()-1; i>=0; i--)
		{
			if(get(i).containsKey(key))
				return get(i);
		}
		return null;
	}
}
