package coneforest.psi.core;

/**
*	A representation of Ψ-{@code arraylike}, an abstraction of an array
*	composed of Ψ-{@code object}s.
*
*	@param <T> a type of contained objects.
*/
public interface PsiArraylike<T extends PsiObject>
	extends
		PsiAppendable<T>,
		PsiContainer<T>,
		PsiIndexed<PsiInteger, T>
{
	/**
	 *	@return a string {@code "arraylike"}.
	 */
	@Override
	default public String typeName()
	{
		return "arraylike";
	}

	@Override
	public PsiArraylike<T> psiClone();

	default public PsiArraylike<T> psiReverse()
		throws PsiException
	{
		PsiArraylike<T> result=psiClone();
		int length=result.length();
		for(int i=0; i<(int)(length/2); i++)
		{
			T o=result.get(i);
			result.put(i, result.get(length-1-i));
			result.put(length-1-i, o);
		}
		return result;
	}

	@Override
	default public PsiBoolean psiKnown(final PsiInteger oIndex)
	{
		long index=oIndex.longValue();
		return PsiBoolean.valueOf(index>=0 && index<length());
	}

	public T get(final int index)
		throws PsiException;

	@Override
	default public T psiGet(final PsiInteger oIndex)
		throws PsiException
	{
		return get(oIndex.intValue());
	}

	public PsiArraylike<T> psiGetInterval(final PsiInteger oIndex, final PsiInteger oLength)
		throws PsiException;

	public void put(final int index, final T o)
		throws PsiException;

	@Override
	default public void psiPut(final PsiInteger oIndex, final T o)
		throws PsiException
	{
		put(oIndex.intValue(), o);
	}

	/**
	*	Inserts the specified Ψ-{@code object} into this array at the position
	*	specified by a given index.
	*
	*	@param index a Ψ-{@code integer} index.
	*	@param o a Ψ-{@code object}.
	*	@throws PsiException when an error occurs.
	*/
	public void insert(final int index, final T o)
		throws PsiException;

	/**
	*	Inserts the specified Ψ-{@code object} into this array at the position
	*	specified by a given Ψ-{@code integer} index.
	*
	*	@param oIndex a Ψ-{@code integer} index.
	*	@param o a Ψ-{@code object}.
	*	@throws PsiException when an error occurs.
	*/
	default public void psiInsert(final PsiInteger oIndex, final T o)
		throws PsiException
	{
		insert(oIndex.intValue(), o);
	}

	default public void psiInsertAll(final PsiInteger oIndex, final PsiIterable<? extends T> oEnumeration)
		throws PsiException
	{
		int index=oIndex.intValue();
		for(T o: (this!=oEnumeration? oEnumeration: (PsiIterable<? extends T>)psiClone()))
			insert(index++, o);
	}

	/**
	*	Inserts the specified Ψ-{@code object} into this array at the
	*	beginning.
	*
	*	@param o a Ψ-{@code object}.
	*	@throws PsiException when an error occurs.
	*/
	default public void psiPrepend(final T o)
		throws PsiException
	{
		insert(0, o);
	}

	default public T psiPreChop()
		throws PsiException
	{
		return extract(0);
	}

	default public T psiPostChop()
		throws PsiException
	{
		return extract(length()-1);
	}

	default public void psiPrependAll(final PsiIterable<? extends T> oEnumeration)
		throws PsiException
	{
		psiInsertAll(PsiInteger.ZERO, oEnumeration);
	}

	@Override
	default public PsiArraylike<T> psiReplicate(final PsiInteger oCount)
		throws PsiException
	{
		long count=oCount.longValue();
		if(count<0)
			throw new PsiRangeCheckException();
		if(count*length()>Integer.MAX_VALUE)
			throw new PsiLimitCheckException();
		PsiArraylike<T> oResult=(PsiArraylike<T>)psiNewEmpty();
		while(count-->0)
			oResult.psiAppendAll(this);
		return oResult;
	}

	default public void psiPutInterval(final PsiInteger oIndex, final PsiIterable<? extends T> oEnumeration)
		throws PsiException
	{
		int index=oIndex.intValue();
		if(index<0
			||
			oEnumeration instanceof PsiLengthy
			&& index+((PsiLengthy)oEnumeration).length()>=length())
			throw new PsiRangeCheckException();
		for(T o: oEnumeration)
		{
			put((int)index++, o);
			if(index==length())
				break;
		}
	}

	@Override
	default public void psiDelete(final PsiInteger oIndex)
		throws PsiException
	{
		delete(oIndex.intValue());
	}

	public void delete(int index)
		throws PsiException;

	@Override
	default public T psiExtract(final PsiInteger oIndex)
		throws PsiException
	{
		return extract(oIndex.intValue());
	}

	@Override
	default public PsiArraylike<T> psiGetAll(final PsiIterable<PsiInteger> oEnumeration)
		throws PsiException
	{
		final PsiArraylike<T> oResult=(PsiArraylike<T>)psiNewEmpty();
		for(PsiInteger oIndex: oEnumeration)
			oResult.psiAppend(psiGet(oIndex));
		return oResult;
	}

	public void psiSetLength(final PsiInteger oLength)
		throws PsiException;

	public T extract(final int index)
		throws PsiException;

	public PsiArraylike<T> psiExtractInterval(final PsiInteger oIndex, final PsiInteger oCount)
		throws PsiException;

	@Override
	public PsiArraylike<T> psiSlice(final PsiIterable<PsiInteger> oIndices)
		throws PsiException;

	@Override
	default public PsiIterable<PsiInteger> psiKeys()
	{
		return new PsiIterable<PsiInteger>()
			{
				@Override
				public java.util.Iterator<PsiInteger> iterator()
				{
					return new java.util.Iterator<PsiInteger>()
						{
							@Override
							public boolean hasNext()
							{
								return index<length();
							}

							@Override
							public PsiInteger next()
							{
								return PsiInteger.valueOf(index++);
							}

							private int index=0;
						};
				}
			};
	}

	@Override
	default public PsiIterable<T> psiValues()
	{
		return this;
	}

	@Override
	default public PsiIterable<PsiObject> psiEntries()
	{
		return new PsiIterable<PsiObject>()
			{
				@Override
				public java.util.Iterator<PsiObject> iterator()
				{
					return new java.util.Iterator<PsiObject>()
						{
							@Override
							public boolean hasNext()
							{
								return parentIterator.hasNext();
							}

							@Override
							public PsiObject next()
							{
								return (flag=!flag)?
									PsiInteger.valueOf(index++): parentIterator.next();
							}

							private boolean flag=false;

							private int index=0;

							private final java.util.Iterator<PsiObject> parentIterator
								=(java.util.Iterator<PsiObject>)PsiArraylike.this.iterator();

						};
				}
			};
	}

	@Override
	default public PsiInteger psiCount()
	{
		return psiLength();
	}

	@Override
	default public String toSyntaxString()
	{
		return "["+toSyntaxStringHelper(this)+"]";
	}

	default public String toSyntaxStringHelper(final PsiLengthy oLengthy)
	{
		final StringBuilder sb=new StringBuilder();
		if(length()>0)
		{
			for(PsiObject obj: this)
			{
				if(obj instanceof PsiLengthy)
					sb.append(obj==oLengthy? "-"+obj.typeName()+"-": ((PsiLengthy)obj).toSyntaxString());
				else
					sb.append(obj.toSyntaxString());
				sb.append(' ');
			}
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
	}
}
