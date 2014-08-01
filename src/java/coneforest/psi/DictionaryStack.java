package coneforest.psi;

public class DictionaryStack
	extends Stack<PsiDictionary>
{
	public PsiObject load(String keyString)
		throws PsiException
	{
		PsiDictionary dict=where(keyString);
		if(dict!=null)
			return dict.psiGet(keyString);
		else
			throw new PsiException("undefined");
	}

	public PsiObject load(PsiStringlike key)
		throws PsiException
	{
		return load(key.getString());
	}
	
	public PsiDictionary where(String keyString)
	{
		for(int i=size()-1; i>=0; i--)
		{
			if(get(i).psiKnown(keyString).getValue())
				return get(i);
		}
		return null;
	}
	
	public PsiDictionary where(PsiStringlike key)
	{
		return where(key.getString());
	}
}
