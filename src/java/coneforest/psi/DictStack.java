package coneforest.psi;
import coneforest.psi.core.*;

/**
 *	An interpreter’s dictionary stack.
 */
public class DictStack
	extends Stack<PsiDictlike<PsiObject>>
{
	public DictStack()
		throws PsiException
	{
		PsiDict systemdict=new PsiSystemDict();
		push(systemdict);
		push((PsiDictlike)systemdict.get("userdict"));
	}

	public PsiObject load(final String key)
		throws PsiException
	{
		PsiDictlike oDict=where(key);
		if(oDict!=null)
			return oDict.get(key);
		else
			throw new PsiUndefinedException();
	}

	public PsiObject load(final PsiStringy oKey)
		throws PsiException
	{
		return load(oKey.stringValue());
	}

	public PsiDictlike where(String key)
	{
		for(int i=size()-1; i>=0; i--)
		{
			PsiDictlike oDict=get(i);
			if(oDict.known(key))
				return oDict;
		}
		return null;
	}

	public PsiDictlike where(PsiStringy oKey)
	{
		return where(oKey.stringValue());
	}

	public void store(String key, PsiObject o)
	{
		PsiDictlike oDict=where(key);
		if(oDict==null)
			oDict=peek();
		oDict.put(key, o);
	}

	public void psiStore(final PsiStringy oKey, final PsiObject o)
	{
		store(oKey.stringValue(), o);
	}

	public void psiBegin(final PsiDictlike oDict)
	{
		push(oDict);
	}
}
