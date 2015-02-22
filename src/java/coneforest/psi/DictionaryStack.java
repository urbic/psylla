package coneforest.psi;

/**
 *	An interpreter’s dictionary stack.
 */
public class DictionaryStack
	extends Stack<PsiDictionarylike>
{
	public DictionaryStack()
	{
		PsiDictionary systemdict=new PsiSystemDictionary();
		push(systemdict);
		try
		{
			push((PsiDictionarylike)systemdict.psiGet("userdict"));
		}
		catch(PsiException e)
		{
			throw new AssertionError();
		}
	}

	public PsiObject load(String keyString)
		throws PsiException
	{
		PsiDictionarylike dict=where(keyString);
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

	public PsiDictionarylike where(String keyString)
	{
		for(int i=size()-1; i>=0; i--)
		{
			PsiDictionarylike dict=get(i);
			if(dict.psiKnown(keyString).booleanValue())
				return dict;
		}
		return null;
	}

	public PsiDictionarylike where(PsiStringlike key)
	{
		return where(key.getString());
	}
}
