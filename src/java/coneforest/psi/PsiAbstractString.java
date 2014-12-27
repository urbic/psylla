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
	public void psiReverse()
		throws PsiException
	{
		int length=length();
		for(int i=0; i<(int)(length/2); i++)
		{
			PsiInteger character=psiGet(i);
			psiPut(i, psiGet(length-1-i));
			psiPut(length-1-i, character);
		}
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
