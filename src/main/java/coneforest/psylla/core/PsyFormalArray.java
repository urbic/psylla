package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of Ψ-{@code formalarray}, an abstraction of an array
*	composed of Ψ-{@code object}s.
*
*	@param <T> a type of contained objects.
*/
@Type("formalarray")
public interface PsyFormalArray<T extends PsyObject>
	extends
		PsyAppendable<T>,
		PsyContainer<T>,
		PsyIndexed<PsyInteger, T>,
		PsySequential<T>
{

	@Override
	public PsyFormalArray<T> psyClone();

	default public PsyFormalArray<T> psyReverse()
		throws PsyException
	{
		final PsyFormalArray<T> result=psyClone();
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
	default public PsyBoolean psyKnown(final PsyInteger oIndex)
	{
		long index=oIndex.longValue();
		return PsyBoolean.valueOf(index>=0 && index<length());
	}

	public T get(final int index)
		throws PsyException;

	@Override
	default public T psyGet(final PsyInteger oIndex)
		throws PsyException
	{
		return get(oIndex.intValue());
	}

	public PsyFormalArray<T> psyGetInterval(final PsyInteger oIndex, final PsyInteger oLength)
		throws PsyException;

	public void put(final int index, final T o)
		throws PsyException;

	@Override
	default public void psyPut(final PsyInteger oIndex, final T o)
		throws PsyException
	{
		put(oIndex.intValue(), o);
	}

	/**
	*	Inserts the specified Ψ-{@code object} into this array at the position
	*	specified by a given index.
	*
	*	@param index a Ψ-{@code integer} index.
	*	@param o a Ψ-{@code object}.
	*	@throws PsyException when an error occurs.
	*/
	public void insert(final int index, final T o)
		throws PsyException;

	/**
	*	Inserts the specified Ψ-{@code object} into this array at the position
	*	specified by a given Ψ-{@code integer} index.
	*
	*	@param oIndex a Ψ-{@code integer} index.
	*	@param o a Ψ-{@code object}.
	*	@throws PsyException when an error occurs.
	*/
	default public void psyInsert(final PsyInteger oIndex, final T o)
		throws PsyException
	{
		insert(oIndex.intValue(), o);
	}

	default public void psyInsertAll(final PsyInteger oIndex, final PsyIterable<? extends T> oEnumeration)
		throws PsyException
	{
		int index=oIndex.intValue();
		for(final T o: (this!=oEnumeration? oEnumeration: (PsyIterable<? extends T>)psyClone()))
			insert(index++, o);
	}

	/**
	*	Inserts the specified Ψ-{@code object} into this array at the
	*	beginning.
	*
	*	@param o a Ψ-{@code object}.
	*	@throws PsyException when an error occurs.
	*/
	default public void psyPrepend(final T o)
		throws PsyException
	{
		insert(0, o);
	}

	default public T psyPreChop()
		throws PsyException
	{
		return extract(0);
	}

	default public T psyPostChop()
		throws PsyException
	{
		return extract(length()-1);
	}

	default public void psyPrependAll(final PsyIterable<? extends T> oEnumeration)
		throws PsyException
	{
		psyInsertAll(PsyInteger.ZERO, oEnumeration);
	}

	@Override
	default public PsyFormalArray<T> psyReplicate(final PsyInteger oCount)
		throws PsyException
	{
		long count=oCount.longValue();
		if(count<0)
			throw new PsyRangeCheckException();
		if(count*length()>Integer.MAX_VALUE)
			throw new PsyLimitCheckException();
		final PsyFormalArray<T> oResult=(PsyFormalArray<T>)psyNewEmpty();
		while(count-->0)
			oResult.psyAppendAll(this);
		return oResult;
	}

	default public void psyPutInterval(final PsyInteger oIndex, final PsyIterable<? extends T> oEnumeration)
		throws PsyException
	{
		int index=oIndex.intValue();
		if(index<0
			||
			oEnumeration instanceof PsyLengthy
			&& index+((PsyLengthy)oEnumeration).length()>=length())
			throw new PsyRangeCheckException();
		for(final T o: oEnumeration)
		{
			put((int)index++, o);
			if(index==length())
				break;
		}
	}

	@Override
	default public void psyDelete(final PsyInteger oIndex)
		throws PsyException
	{
		delete(oIndex.intValue());
	}

	public void delete(int index)
		throws PsyException;

	@Override
	default public T psyExtract(final PsyInteger oIndex)
		throws PsyException
	{
		return extract(oIndex.intValue());
	}

	@Override
	default public PsyFormalArray<T> psyGetAll(final PsyIterable<PsyInteger> oIndices)
		throws PsyException
	{
		final PsyFormalArray<T> oResult=(PsyFormalArray<T>)psyNewEmpty();
		for(final PsyInteger oIndex: oIndices)
			oResult.psyAppend(psyGet(oIndex));
		return oResult;
	}

	public void psySetLength(final PsyInteger oLength)
		throws PsyException;

	public T extract(final int index)
		throws PsyException;

	public PsyFormalArray<T> psyExtractInterval(final PsyInteger oIndex, final PsyInteger oCount)
		throws PsyException;

	@Override
	public PsyFormalArray<T> psySlice(final PsyIterable<PsyInteger> oIndices)
		throws PsyException;

	@Override
	default public PsyStream psyKeys()
	{
		return new PsyStream(java.util.stream.IntStream.range(0, length()).mapToObj(PsyInteger::valueOf));
	}

	@Override
	default public PsyFormalStream<T> psyValues()
	{
		return new PsyStream(java.util.stream.StreamSupport.stream(spliterator(), false));
	}

	@Override
	default public PsyFormalStream<PsyObject> psyEntries()
	{
		return null; // TODO
		/*return new PsyFormalStream<PsyObject>()
			{
				@Override
				public void psyForAll(final PsyObject oProc)
					throws PsyException
				{
					final var interpreter=PsyContext.psyCurrentContext();
					final var ostack=interpreter.operandStack();
					final java.util.Iterator<PsyInteger> iterator=psyKeys().iterator();
					interpreter.pushLoopLevel();
					interpreter.executionStack().push(new PsyOperator("#forall_continue")
						{
							@Override
							public void action()
								throws PsyException
							{
								final var interpreter1=PsyContext.psyCurrentContext();
								if(iterator.hasNext())
								{
									final PsyInteger oIndex=iterator.next();
									ostack.push(oIndex);
									ostack.push(psyGet(oIndex));
									interpreter1.executionStack().push(this);
									oProc.invoke();
								}
								else
								{
									interpreter1.popLoopLevel();
								}
							}
						});
				}

				@Override
				public java.util.stream.Stream<PsyObject> stream()
				{
					return new java.util.stream.Stream<PsyObject>()
						{

							@Override
							public java.util.Iterator<PsyObject> iterator()
							{
								return new java.util.Iterator<PsyObject>()
									{
										@Override
										public boolean hasNext()
										{
											return parentIterator.hasNext();
										}

										@Override
										public PsyObject next()
										{
											return (flag=!flag)?
												PsyInteger.valueOf(index++): parentIterator.next();
										}

										private boolean flag=false;

										private int index=0;

										private final java.util.Iterator<PsyObject> parentIterator
											=(java.util.Iterator<PsyObject>)PsyFormalArray.this.iterator();

									};
							}
						};
				}
			};
		*/
	}

	@Override
	default public String toSyntaxString()
	{
		return toSyntaxStringHelper(new java.util.HashSet<PsyContainer<T>>());
	}

	@Override
	default public String toSyntaxStringHelper(final java.util.Set<PsyContainer<T>> processed)
	{
		if(!processed.add((PsyContainer<T>)this))
			return '%'+typeName()+'%';
		final var sj=new java.util.StringJoiner(" ", "[", "]");
		for(final var o: this)
			sj.add(o instanceof PsyContainer?
				((PsyContainer<T>)o).toSyntaxStringHelper(processed):
				o.toSyntaxString());
		return sj.toString();
	}

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity31<PsyFormalArray, PsyInteger, PsyInteger>
				("extractinterval", PsyFormalArray::psyExtractInterval),
			new PsyOperator.Arity31<PsyFormalArray, PsyInteger, PsyInteger>
				("getinterval", PsyFormalArray::psyGetInterval),
			new PsyOperator.Arity30<PsyFormalArray, PsyInteger, PsyObject>
				("insert", PsyFormalArray::psyInsert),
			new PsyOperator.Arity30<PsyFormalArray, PsyInteger, PsyIterable>
				("insertall", PsyFormalArray::psyInsertAll),
			new PsyOperator.Arity11<PsyFormalArray>
				("postchop", PsyFormalArray::psyPostChop),
			new PsyOperator.Arity11<PsyFormalArray>
				("prechop", PsyFormalArray::psyPreChop),
			new PsyOperator.Arity20<PsyFormalArray, PsyObject>
				("prepend", PsyFormalArray::psyPrepend),
			new PsyOperator.Arity20<PsyFormalArray, PsyIterable>
				("prependall", PsyFormalArray::psyPrependAll),
			new PsyOperator.Arity30<PsyFormalArray, PsyInteger, PsyIterable>
				("putinterval", PsyFormalArray::psyPutInterval),
			new PsyOperator.Arity11<PsyFormalArray>
				("reverse", PsyFormalArray::psyReverse),
			new PsyOperator.Arity20<PsyFormalArray, PsyInteger>
				("setlength", PsyFormalArray::psySetLength),
		};

}