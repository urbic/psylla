package coneforest.psi;

abstract public class PsiAbstractDictionary<V extends PsiObject>
	extends PsiObject
	implements PsiDictionarylike<V>
{
	@Override
	public String getTypeName()
	{
		return "dict";
	}

	//@Override
	//abstract public V psiGet(String keyString)
	//	throws PsiException;

	@Override
	public V psiGet(PsiStringlike key)
		throws PsiException
	{
		return psiGet(key.getString());
	}

	//@Override
	//abstract public void psiPut(String keyString, V obj);

	@Override
	public void psiPut(PsiStringlike key, V obj)
	{
		psiPut(key.getString(), obj);
	}

	/*
	@Override
	abstract public PsiBoolean psiKnown(String keyString);
	*/

	@Override
	public PsiBoolean psiKnown(PsiStringlike key)
	{
		return psiKnown(key.getString());
	}

	@Override
	abstract public void psiUndef(String keyString);

	@Override
	public void psiUndef(PsiStringlike key)
	{
		psiUndef(key.getString());
	}
}
