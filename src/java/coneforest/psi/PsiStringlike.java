package coneforest.psi;

abstract public class PsiStringlike
	extends PsiObject
	implements PsiComposite<PsiInteger>
{
	public PsiStringlike()
	{
	}

	public PsiStringlike(String value)
	{
		setValue(value);
	}
	
	abstract public String getValue();
	
	abstract public void setValue(final String value);
	
	abstract public String toString();

	public int size()
	{
		return getValue().length();
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

	//private String value;
}
