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
		final PsiDict oSystemDict=new PsiSystemDict();
		push(oSystemDict);
		push((PsiDictlike)oSystemDict.get("userdict"));
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

	public PsiNamespace currentNamespace()
	{
		for(int i=size()-1; i>=0; i--)
		{
			final PsiDictlike oDict=get(i);
			if(oDict instanceof PsiNamespace)
				return (PsiNamespace)oDict;
		}
		return null; // TODO
	}

	public void store(final String key, final PsiObject oValue)
	{
		PsiDictlike oDict=where(key);
		if(oDict==null)
			oDict=peek();
		oDict.put(key, oValue);
	}

	public void psiStore(final PsiStringy oKey, final PsiObject oValue)
	{
		store(oKey.stringValue(), oValue);
	}

	public void psiBegin(final PsiDictlike oDict)
	{
		push(oDict);
	}

	public void psiEnd()
		throws PsiDictStackUnderflowException
	{
		if(size()<=2)
			throw new PsiDictStackUnderflowException();
		pop();
	}
}
