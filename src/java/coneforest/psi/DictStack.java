package coneforest.psi;

/**
 *	An interpreter’s dictionary stack.
 */
public class DictStack
	extends Stack<PsiDictlike>
{
	public DictStack()
	{
		PsiDict systemdict=new PsiSystemDict();
		push(systemdict);
		try
		{
			push((PsiDictlike)systemdict.get("userdict"));
		}
		catch(PsiException e)
		{
			throw new AssertionError();
		}
	}

	public PsiObject load(String keyString)
		throws PsiException
	{
		PsiDictlike dict=where(keyString);
		if(dict!=null)
			return dict.get(keyString);
		else
			throw new PsiException("undefined");
	}

	public PsiObject load(PsiStringlike key)
		throws PsiException
	{
		return load(key.getString());
	}

	public PsiDictlike where(String keyString)
	{
		for(int i=size()-1; i>=0; i--)
		{
			PsiDictlike dict=get(i);
			if(dict.known(keyString))
				return dict;
		}
		return null;
	}

	public PsiDictlike where(PsiStringlike key)
	{
		return where(key.getString());
	}
}
