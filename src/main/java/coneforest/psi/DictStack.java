package coneforest.psi;
import coneforest.psi.core.*;

/**
*	An interpreter’s dictionary stack.
*/
public class DictStack
	extends Stack<PsiDictlike<PsiObject>>
{
	/**
	*	Creates a new dictionary stack with two dictionaries in it (system and
	*	user dictionaries).
	*
	*	@throws PsiException when the error occur.
	*/
	public DictStack()
		throws PsiException
	{
		final PsiDict systemdict=new PsiSystemDict();
		push(systemdict);
		push((PsiDictlike)systemdict.get("userdict"));
	}

	public <T extends PsiObject> T load(final String key)
		throws PsiException
	{
		final PsiDictlike oDict=where(key);
		if(oDict!=null)
			return (T)oDict.get(key);
		else
			throw new PsiUndefinedException();
	}

	public <T extends PsiObject> T load(final PsiStringy oKey)
		throws PsiException
	{
		return load(oKey.stringValue());
	}

	public PsiDictlike where(final String key)
	{
		for(int i=size()-1; i>=0; i--)
		{
			final PsiDictlike oDict=get(i);
			if(oDict.known(key))
				return oDict;
		}
		return null;
	}

	public PsiDictlike where(final PsiStringy oKey)
	{
		return where(oKey.stringValue());
	}

	public void store(final String key, final PsiObject o)
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
