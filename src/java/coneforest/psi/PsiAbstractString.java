package coneforest.psi;

abstract public class PsiAbstractString
	extends PsiAbstractStringlike
	//extends PsiAbstractArray<PsiInteger>
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
