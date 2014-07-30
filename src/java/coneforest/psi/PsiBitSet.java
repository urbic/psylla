package coneforest.psi;

public class PsiBitSet
	extends PsiAbstractSet<PsiInteger>
{
	@Override
	public String getTypeName()
	{
		return "bitset";
	}

	public java.util.BitSet getBitSet()
	{
		return bitset;
	}

	@Override
	public void psiAppend(PsiInteger index)
		throws PsiException
	{
		try
		{
			bitset.set(index.getValue().intValue(), true);
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiException("rangecheck");
		}
	}

	@Override
	public void psiAppendAll(PsiIterable<? extends PsiInteger> iterable)
		throws PsiException
	{
		if(iterable instanceof PsiBitSet)
			bitset.or(((PsiBitSet)iterable).bitset);
		else
			super.psiAppendAll(iterable);
	}

	@Override
	public void psiRemove(PsiInteger integer)
		throws PsiException
	{
		try
		{
			bitset.set(integer.getValue().intValue(), false);
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiException("rangecheck");
		}
	}

	@Override
	public void psiRemoveAll(PsiIterable<? extends PsiInteger> iterable)
		throws PsiException
	{
		if(iterable instanceof PsiBitSet)
			bitset.andNot(((PsiBitSet)iterable).bitset);
		else
			super.psiRemoveAll(iterable);
	}

	public java.util.Iterator<PsiInteger> iterator()
	{
		return new java.util.Iterator<PsiInteger>()
			{
				public boolean hasNext()
				{
					return index>=0;
				}

				public PsiInteger next()
				{
					if(hasNext())
					{
						PsiInteger result=new PsiInteger(index);
						index=bitset.nextSetBit(index+1);
						return result;
					}
					else
						throw new java.util.NoSuchElementException();
				}

				public void remove()
				{
					throw new UnsupportedOperationException();
				}

				private int index=bitset.nextSetBit(0);
			};
	}

	@Override
	public PsiInteger psiLength()
	{
		return new PsiInteger(bitset.cardinality());
	}

	@Override
	public PsiBoolean psiIsEmpty()
	{
		return new PsiBoolean(bitset.isEmpty());
	}

	@Override
	public PsiBoolean psiContains(PsiInteger integer)
	{
		return new PsiBoolean(bitset.get(integer.getValue().intValue()));
	}

	@Override
	public PsiBoolean psiIntersects(PsiSetlike setlike)
	{
		if(setlike instanceof PsiBitSet)
			return new PsiBoolean(bitset.intersects(((PsiBitSet)setlike).getBitSet()));
		else
			return super.psiIntersects(setlike);
	}

	@Override
	public PsiBoolean psiEq(final PsiObject obj)
	{
		return new PsiBoolean(obj instanceof PsiBitSet
				&& bitset.equals(((PsiBitSet)obj).getBitSet()));
	}

	private java.util.BitSet bitset=new java.util.BitSet();
}
