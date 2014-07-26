package coneforest.psi;

abstract public class PsiStringlike
	extends PsiObject
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

	public PsiInteger length()
	{
		return new PsiInteger(getValue().length());
	}

	public PsiBoolean isEmpty()
	{
		return new PsiBoolean(getValue().isEmpty());
	}

	public java.util.Iterator<PsiInteger> iterator()
	{
		return new java.util.Iterator<PsiInteger>()
			{
				public boolean hasNext()
				{
					return index<getValue().length();
				}

				public PsiInteger next()
				{
					if(hasNext())
						return new PsiInteger(getValue().charAt(index++));
					else
						throw new java.util.NoSuchElementException();
				}

				public void remove()
				{
					throw new UnsupportedOperationException();
				}

				private int index=0;
			};
	}
	
	public PsiBoolean eq(final PsiObject obj)
	{
		return new PsiBoolean(obj instanceof PsiStringlike
				&& getValue().compareTo(((PsiStringlike)obj).getValue())==0);
	}

	/*
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
	*/

	//private String value;
}
