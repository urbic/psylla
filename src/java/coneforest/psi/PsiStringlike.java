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

	@Override
	public PsiInteger psiLength()
	{
		return new PsiInteger(getValue().length());
	}

	@Override
	public PsiBoolean psiIsEmpty()
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

	@Override
	public PsiBoolean psiEq(final PsiObject obj)
	{
		return new PsiBoolean(obj instanceof PsiStringlike
				&& getValue().compareTo(((PsiStringlike)obj).getValue())==0);
	}
}
