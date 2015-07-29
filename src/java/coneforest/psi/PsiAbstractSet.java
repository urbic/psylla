package coneforest.psi;

abstract public class PsiAbstractSet<T extends PsiObject>
	implements PsiSetlike<T>
{
	@Override
	public String toSyntaxString()
	{
		return toSyntaxStringHelper(this);
	}

	//@Override
	public String toSyntaxStringHelper(PsiLengthy lengthy)
	{
		StringBuilder sb=new StringBuilder("(");
		if(length()>0)
		{
			for(PsiObject obj: this)
			{
				if(obj instanceof PsiLengthy)
					sb.append(obj==lengthy? "-"+obj.getTypeName()+"-": ((PsiLengthy)obj).toSyntaxString());
				else
					sb.append(obj.toString());
				sb.append(' ');
			}
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append(')');
		return sb.toString();
	}

	@Override
	public PsiAbstractSet<T> psiNewEmpty()
		throws PsiException
	{
		try
		{
			return getClass().newInstance();
		}
		catch(InstantiationException|IllegalAccessException e)
		{
			throw new PsiException("unknownerror");
		}
	}

	@Override
	public PsiAbstractSet<T> psiReplicate(PsiInteger count)
		throws PsiException
	{
		long countValue=count.longValue();
		if(countValue<0)
			throw new PsiException("rangecheck");
		if(countValue>Integer.MAX_VALUE)
			throw new PsiException("limitcheck");
		if(countValue==0)
			return psiNewEmpty();
		return psiClone();
	}

	@Override
	abstract public PsiAbstractSet<T> psiClone();
}
