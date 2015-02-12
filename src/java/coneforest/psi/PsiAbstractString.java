package coneforest.psi;

abstract public class PsiAbstractString
	extends PsiAbstractStringlike
	implements
		PsiArraylike<PsiInteger>,
		PsiStringlike
{
	@Override
	public String getTypeName()
	{
		return "string";
	}

	@Override
	public PsiInteger psiGet(PsiInteger index)
		throws PsiException
	{
		return psiGet(index.intValue());
	}

	@Override
	public void psiPut(PsiInteger index, PsiInteger character)
		throws PsiException
	{
		psiPut(index.intValue(), character);
	}

	@Override
	public void psiInsert(PsiInteger index, PsiInteger character)
		throws PsiException
	{
		psiInsert(index.intValue(), character);
	}

	@Override
	public PsiInteger psiDelete(PsiInteger index)
		throws PsiException
	{
		return psiDelete(index.intValue());
	}

	@Override
	abstract public PsiAbstractString psiCloneEmpty();

	@Override
	abstract public PsiAbstractString psiClone();

	@Override
	public PsiAbstractString psiReplicate(final PsiInteger count)
		throws PsiException
	{
		int countValue=count.intValue();
		if(countValue<0)
			throw new PsiException("rangecheck");
		PsiAbstractString result=psiCloneEmpty();
		while(countValue-->0)
			result.psiAppendAll(this);
		return result;
	}

	@Override
	public PsiAbstractString psiReverse()
		throws PsiException
	{
		PsiAbstractString result=psiClone();
		int length=result.length();
		for(int i=0; i<(int)(length/2); i++)
		{
			PsiInteger character=result.psiGet(i);
			result.psiPut(i, result.psiGet(length-1-i));
			result.psiPut(length-1-i, character);
		}
		return result;
	}

	@Override
	public String toString()
	{
		StringBuilder sb=new StringBuilder();
		String string=getString();
		for(int i=0; i<string.length(); i++)
		{
			char c=string.charAt(i);
			switch(c)
			{
				case '\u0000':
					sb.append("\\0");
					break;
				case '\u0007':
					sb.append("\\a");
					break;
				case '\n':
					sb.append("\\n");
					break;
				case '\r':
					sb.append("\\r");
					break;
				case '\t':
					sb.append("\\t");
					break;
				case '\f':
					sb.append("\\f");
					break;
				case '\u001B':
					sb.append("\\e");
					break;
				case '\"':
					sb.append("\\\"");
					break;
				case '\\':
					sb.append("\\\\");
					break;
				default:
					sb.append(c);
			}
		}
		return "\""+sb.toString()+"\"";
	}

	@Override
	public boolean equals(Object object)
	{
		return getClass().isInstance(object)
				&& psiEq((PsiString)object).booleanValue();
	}
}
