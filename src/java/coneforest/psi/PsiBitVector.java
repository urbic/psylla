package coneforest.psi;

public class PsiBitVector
	extends PsiObject
	implements PsiArraylike<PsiBoolean>, PsiLogical<PsiBitVector>
{
	public PsiBitVector()
	{
		this(new java.util.BitSet());
	}

	public PsiBitVector(java.util.BitSet bitvector)
	{
		this.bitvector=bitvector;
	}

	public String getTypeName()
	{
		return "bitvector";
	}

	public String toString()
	{
		return "-bitvector-";
	}

	private java.util.BitSet getBitVector()
	{
		return bitvector;
	}

	@Override
	public PsiBoolean psiGet(PsiInteger integer)
		throws PsiException
	{
		try
		{
			return new PsiBoolean(bitvector.get(integer.getValue().intValue()));
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiException("rangecheck");
		}
	}

	@Override
	public void psiPut(PsiInteger integer, PsiBoolean bool)
		throws PsiException
	{
		try
		{
			bitvector.set(integer.getValue().intValue(), bool.getValue());
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new PsiException("rangecheck");
		}
	}

	@Override
	public void psiAppend(PsiBoolean bool)
		throws PsiException
	{
		throw new PsiException("TODO");
	}

	@Override
	public void psiAppendAll(PsiIterable<? extends PsiBoolean> iterable)
		throws PsiException
	{
		throw new PsiException("TODO");
	}

	@Override
	public void psiInsert(PsiInteger index, PsiBoolean bool)
		throws PsiException
	{
		throw new PsiException("TODO");
	}

	@Override
	public void psiInsertAll(PsiInteger index, PsiIterable<? extends PsiBoolean> iterable)
		throws PsiException
	{
		throw new PsiException("TODO");
	}

	@Override
	public PsiBitVector psiNot()
	{
		java.util.BitSet result=(java.util.BitSet)bitvector.clone();
		result.flip(0, result.size());
		return new PsiBitVector(result);
	}

	@Override
	public PsiBitVector psiOr(final PsiBitVector obj)
	{
		java.util.BitSet result=(java.util.BitSet)bitvector.clone();
		result.or(obj.getBitVector());
		return new PsiBitVector(result);
	}

	@Override
	public PsiBitVector psiAnd(final PsiBitVector obj)
	{
		java.util.BitSet result=(java.util.BitSet)bitvector.clone();
		result.and(obj.getBitVector());
		return new PsiBitVector(result);
	}

	@Override
	public PsiBitVector psiXor(final PsiBitVector obj)
	{
		java.util.BitSet result=(java.util.BitSet)bitvector.clone();
		result.xor(obj.getBitVector());
		return new PsiBitVector(result);
	}

	public java.util.Iterator<PsiBoolean> iterator()
	{
		return new java.util.Iterator<PsiBoolean>()
			{
				public boolean hasNext()
				{
					return index<bitvector.size(); // TODO ???
				}

				public PsiBoolean next()
				{
					if(hasNext())
						return new PsiBoolean(bitvector.get(index++));
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
	public PsiInteger psiLength()
	{
		return new PsiInteger(bitvector.size());
	}

	@Override
	public PsiBoolean psiIsEmpty()
	{
		return new PsiBoolean(bitvector.size()==0);
	}

	private java.util.BitSet bitvector;
}
