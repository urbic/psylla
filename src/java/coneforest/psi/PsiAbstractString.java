package coneforest.psi;

abstract public class PsiAbstractString
	extends PsiAbstractStringlike
	implements PsiArraylike<PsiInteger>
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
		return psiGet(index.getValue().intValue());
	}

	@Override
	public void psiPut(PsiInteger index, PsiInteger character)
		throws PsiException
	{
		psiPut(index.getValue().intValue(), character);
	}

	@Override
	public void psiInsert(PsiInteger index, PsiInteger character)
		throws PsiException
	{
		psiInsert(index.getValue().intValue(), character);
	}

	/*
	@Override
	public void psiInsertAll(PsiInteger index, PsiIterable<PsiInteger> iterable)
		throws PsiException
	{
		psiInsertAll(index.getValue().intValue(), iterable);
	}
	*/

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
