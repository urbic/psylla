package coneforest.psi;

abstract public class PsiAbstractString
	extends PsiAbstractStringlike
	implements
		PsiArraylike<PsiInteger>,
		PsiScalar<PsiString>
	/*
	extends PsiObject
	implements
		PsiStringlike,
		PsiArraylike<PsiInteger>,
		PsiScalar<PsiString>
	*/
{
	/*public PsiAbstractString()
	{
	}*/

	//abstract public PsiAbstractString(String value);
	/*{
		setValue(value);
	}*/

	//@Override
	//abstract public String getString();

	//@Override
	//abstract public void setValue(final String value);

	@Override
	public java.util.Iterator<PsiInteger> iterator()
	{
		return new java.util.Iterator<PsiInteger>()
			{
				public boolean hasNext()
				{
					return index<getString().length();
				}

				public PsiInteger next()
				{
					if(hasNext())
						return new PsiInteger(getString().charAt(index++));
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
				&& getString().equals(((PsiStringlike)obj).getString()));
	}
}
