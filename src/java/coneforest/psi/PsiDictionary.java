package coneforest.psi;

/**
 *	A representation of Ψ <code class="type">dictionary</code> object.
 */
public class PsiDictionary
	extends PsiAbstractDictionary<PsiObject>
{
	public PsiDictionary()
	{
	}

	public PsiDictionary(PsiDictionary dict)
	{
		this.dictionary=(java.util.HashMap<String, PsiObject>)dict.dictionary.clone();
	}

	@Override
	public PsiDictionary psiClone()
	{
		return new PsiDictionary(this);
	}

	@Override
	public PsiObject psiGet(String key)
		throws PsiException
	{
		PsiObject result=dictionary.get(key);
		if(result!=null)
			return result;
		else
			throw new PsiException("undefined");
	}

	@Override
	public void psiPut(String keyString, PsiObject obj)
	{
		dictionary.put(keyString, obj);
	}

	@Override
	public void psiUndef(String keyString)
	{
		dictionary.remove(keyString);
	}

	@Override
	public PsiBoolean psiKnown(String keyString)
	{
		return PsiBoolean.valueOf(dictionary.containsKey(keyString));
	}

	@Override
	public java.util.Iterator<java.util.Map.Entry<String, PsiObject>> iterator()
	{
		return dictionary.entrySet().iterator();
	}

	@Override
	public int length()
	{
		return dictionary.size();
	}

	@Override
	public PsiInteger psiLength()
	{
		return new PsiInteger(length());
	}

	@Override
	public boolean isEmpty()
	{
		return dictionary.isEmpty();
	}

	@Override
	public PsiBoolean psiIsEmpty()
	{
		return PsiBoolean.valueOf(isEmpty());
	}

	@Override
	public void psiClear()
	{
		dictionary.clear();
	}

	@Override
	public PsiDictionary psiSlice(PsiIterable<PsiStringlike> keys)
		throws PsiException
	{
		PsiDictionary values=new PsiDictionary();
		for(PsiStringlike key: keys)
			values.psiPut(key, psiGet(key));
		return values;
	}

	private java.util.HashMap<String, PsiObject> dictionary
		=new java.util.HashMap<String, PsiObject>();
}
