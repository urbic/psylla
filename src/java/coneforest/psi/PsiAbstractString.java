package coneforest.psi;

abstract public class PsiAbstractString
	extends PsiAbstractStringlike
	implements PsiArraylike<PsiInteger>
{
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
}
