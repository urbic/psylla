package coneforest.psi;

/**
*	A representation of Ψ-{@code arraylike}, an abstraction of an array
*	composed of Ψ-{@code object}s.
*
*	@param <T> a type of contained objects.
*/
public interface PsiArraylike<T extends PsiObject>
	extends
		PsiContainer<T>,
		PsiIndexed<PsiInteger, T>,
		PsiAppendable<T>,
		PsiClearable
{
	/**
	 *	@return a string {@code "arraylike"}.
	 */
	@Override
	default public String getTypeName()
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
	default public PsiBoolean psiKnown(PsiInteger oIndex)
	{
		long index=oIndex.longValue();
		return PsiBoolean.valueOf(index>=0 && index<length());
	}

	public T get(int indexValue)
		throws PsiException;

	@Override
	default public T psiGet(PsiInteger oIndex)
		throws PsiException
	{
		return get(oIndex.intValue());
	}

	public PsiArraylike<T> psiGetInterval(PsiInteger oIndex, PsiInteger oLength)
		throws PsiException;

	public void put(int indexValue, T obj)
		throws PsiException;

	@Override
	default public void psiPut(PsiInteger oIndex, T o)
		throws PsiException
	{
		put(oIndex.intValue(), o);
	}

	public void insert(int index, T o)
		throws PsiException;

	default public void psiInsert(PsiInteger oIndex, T o)
		throws PsiException
	{
		insert(oIndex.intValue(), o);
	}

	default public void psiInsertAll(PsiInteger oIndex, PsiIterable<? extends T> oEnumeration)
		throws PsiException
	{
		int index=oIndex.intValue();
		for(T o: (this!=oEnumeration? oEnumeration: (PsiIterable<? extends T>)psiClone()))
			insert(index++, o);
	}

	default public void psiPrepend(T o)
		throws PsiException
	{
		insert(0, o);
	}

	default public void psiPrependAll(PsiIterable<? extends T> oEnumeration)
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

	default public void psiPutInterval(PsiInteger oIndex, PsiIterable<? extends T> oEnumeration)
		throws PsiException
	{
		long index=oIndex.intValue();
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
	default public PsiArraylike<T> psiGetAll(PsiIterable<PsiInteger> oEnumeration)
		throws PsiException
	{
		PsiArraylike<T> oResult=(PsiArraylike<T>)psiNewEmpty();
		for(PsiInteger oIndex: oEnumeration)
			oResult.psiAppend(psiGet(oIndex));
		return oResult;
	}

	public void psiSetLength(PsiInteger oLength)
		throws PsiException;

	public T extract(int index)
		throws PsiException;

	public PsiArraylike<T> psiExtractInterval(PsiInteger oIndex, PsiInteger oCount)
		throws PsiException;

	@Override
	public PsiArraylike<T> psiSlice(PsiIterable<PsiInteger> oIndices)
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

							private java.util.Iterator<PsiObject> parentIterator
								=(java.util.Iterator<PsiObject>)PsiArraylike.this.iterator();

						};
				}
			};
	}

	@Override
	default public String toSyntaxString()
	{
		return "["+toSyntaxStringHelper(this)+"]";
	}

	default public String toSyntaxStringHelper(PsiLengthy lengthy)
	{
		StringBuilder sb=new StringBuilder();
		if(length()>0)
		{
			for(PsiObject obj: this)
			{
				if(obj instanceof PsiLengthy)
					sb.append(obj==lengthy? "-"+obj.getTypeName()+"-": ((PsiLengthy)obj).toSyntaxString());
				else
					sb.append(obj.toSyntaxString());
				sb.append(' ');
			}
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
	}
}
