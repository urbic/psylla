package coneforest.psi;

public class DictionaryStack extends Stack<PsiDictionary>
{
	public PsiObject load(PsiStringlike key)
		throws PsiException
	{
		PsiDictionary dict=where(key);
		if(dict!=null)
			return dict.psiGet(key);
		else
			throw new PsiException("undefined");
	}

	public PsiDictionary where(PsiStringlike key)
	{
		for(int i=size()-1; i>=0; i--)
		{
			if(get(i).psiKnown(key).getValue())
				return get(i);
		}
		return null;
	}
}
