package coneforest.psi;

public class DictionaryStack extends Stack<PsiDictionary>
{
	public PsiObject load(PsiStringlike oKey)
		throws PsiException
	{
		for(int i=size()-1; i>=0; i--)
		{
			if(get(i).containsKey(oKey))
				return get(i).get(oKey);
		}
		throw new PsiException("undefined");
	}

	public PsiDictionary where(PsiStringlike oKey)
	{
		for(int i=size()-1; i>=0; i--)
		{
			if(get(i).containsKey(oKey))
				return get(i);
		}
		return null;
	}
}
