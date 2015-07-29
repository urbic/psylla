package coneforest.psi;

abstract public class PsiAbstractString
	implements
		PsiStringlike,
		PsiArraylike<PsiInteger>
{
	@Override
	public String getTypeName()
	{
		return "string";
	}

	@Override
	abstract public PsiAbstractString psiClone();

	@Override
	public String toSyntaxString()
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

	@Override
	public int hashCode()
	{
		return getString().hashCode();
	}
}
