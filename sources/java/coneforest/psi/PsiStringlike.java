package coneforest.psi;

abstract public class PsiStringlike extends PsiObject
{
	public PsiStringlike()
	{
	}
	
	public PsiStringlike(String value)
	{
		this.value=value;
	}
	
	public String getValue()
	{
		return value;
	}
	
	public void setValue(final String value)
	{
		this.value=value;
	}
	
	public String toString()
	{
		if(this instanceof PsiString)
			return ((PsiString)this).toString();
		else
			return ((PsiName)this).toString();
	}
	
	public static PsiBoolean eq(final PsiStringlike x, final PsiStringlike y)
	{
		return new PsiBoolean(x.getValue().compareTo(y.getValue())==0);
	}

	public static PsiBoolean ne(final PsiStringlike x, final PsiStringlike y)
	{
		return new PsiBoolean(x.getValue().compareTo(y.getValue())!=0);
	}

	public static PsiBoolean lt(final PsiStringlike x, final PsiStringlike y)
	{
		return new PsiBoolean(x.getValue().compareTo(y.getValue())<0);
	}

	public static PsiBoolean le(final PsiStringlike x, final PsiStringlike y)
	{
		return new PsiBoolean(x.getValue().compareTo(y.getValue())<=0);
	}

	public static PsiBoolean gt(final PsiStringlike x, final PsiStringlike y)
	{
		return new PsiBoolean(x.getValue().compareTo(y.getValue())>0);
	}

	public static PsiBoolean ge(final PsiStringlike x, final PsiStringlike y)
	{
		return new PsiBoolean(x.getValue().compareTo(y.getValue())>=0);
	}

	private String value;
}
