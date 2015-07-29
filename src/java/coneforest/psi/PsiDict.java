package coneforest.psi;

/**
 *	A representation of Ψ-<code class="type">dictionary</code> object.
 */
public class PsiDict
	implements PsiDictlike<PsiObject>
{
	@Override
	public String getTypeName()
	{
		return "dict";
	}

	public PsiDict()
	{
		this.dictionary=new java.util.HashMap<String, PsiObject>();
	}

	public PsiDict(PsiDict dict)
	{
		this.dictionary=(java.util.HashMap<String, PsiObject>)dict.dictionary.clone();
	}

	@Override
	public PsiDict psiClone()
	{
		return new PsiDict(this);
	}

	@Override
	public PsiObject get(String key)
		throws PsiException
	{
		PsiObject result=dictionary.get(key);
		if(result!=null)
			return result;
		else
			throw new PsiException("undefined");
	}

	@Override
	public void put(String keyString, PsiObject obj)
	{
		dictionary.put(keyString, obj);
	}

	@Override
	public void undef(String keyString)
	{
		dictionary.remove(keyString);
	}

	@Override
	public boolean known(String keyString)
	{
		return dictionary.containsKey(keyString);
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
	public boolean isEmpty()
	{
		return dictionary.isEmpty();
	}

	@Override
	public void psiClear()
	{
		dictionary.clear();
	}

	@Override
	public PsiDict psiSlice(PsiIterable<PsiStringlike> keys)
		throws PsiException
	{
		PsiDict values=new PsiDict();
		for(PsiStringlike key: keys)
			values.psiPut(key, psiGet(key));
		return values;
	}

	private java.util.HashMap<String, PsiObject> dictionary;
}
