package coneforest.psylla.core.types;

import coneforest.psylla.Type;
import coneforest.psylla.core.errors.PsyError;
import coneforest.psylla.core.errors.PsyLimitCheck;
import coneforest.psylla.core.errors.PsyRangeCheck;

/**
*	A representation of {@code formalarray}, an abstraction of an array
*	composed of {@code object}s.
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
		throws PsyError
	{
		final PsyFormalArray<T> result=psyClone();
		int length=result.length();
		for(int i=0; i<length/2; i++)
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
		return PsyBoolean.of(index>=0 && index<length());
	}

	public T get(final int index)
		throws PsyError;

	@Override
	default public T psyGet(final PsyInteger oIndex)
		throws PsyError
	{
		return get(oIndex.intValue());
	}

	public PsyFormalArray<T> psyGetInterval(final PsyInteger oIndex, final PsyInteger oLength)
		throws PsyError;

	public void put(final int index, final T o)
		throws PsyError;

	@Override
	default public void psyPut(final PsyInteger oIndex, final T o)
		throws PsyError
	{
		put(oIndex.intValue(), o);
	}

	/**
	*	Inserts the specified {@code object} into this array at the position
	*	specified by a given index.
	*
	*	@param index a {@code integer} index.
	*	@param o a {@code object}.
	*
	*	@throws PsyError when an error occurs.
	*/
	public void insert(final int index, final T o)
		throws PsyError;

	/**
	*	Inserts the specified {@code object} into this array at the position
	*	specified by a given {@code integer} index.
	*
	*	@param oIndex an {@code integer} index.
	*	@param o an {@code object}.
	*
	*	@throws PsyError when an error occurs.
	*/
	default public void psyInsert(final PsyInteger oIndex, final T o)
		throws PsyError
	{
		insert(oIndex.intValue(), o);
	}

	default public void psyInsertAll(final PsyInteger oIndex, final PsyIterable<? extends T> oEnumeration)
		throws PsyError
	{
		int index=oIndex.intValue();
		for(final T o: (this!=oEnumeration? oEnumeration: (PsyIterable<? extends T>)psyClone()))
			insert(index++, o);
	}

	/**
	*	Inserts the specified {@code object} into this array at the beginning.
	*
	*	@param o an {@code object}.
	*	@throws PsyError when an error occurs.
	*/
	default public void psyPrepend(final T o)
		throws PsyError
	{
		insert(0, o);
	}

	default public T psyPreChop()
		throws PsyError
	{
		return extract(0);
	}

	default public T psyPostChop()
		throws PsyError
	{
		return extract(length()-1);
	}

	default public void psyPrependAll(final PsyIterable<? extends T> oEnumeration)
		throws PsyError
	{
		psyInsertAll(PsyInteger.ZERO, oEnumeration);
	}

	@Override
	default public PsyFormalArray<T> psyReplicate(final PsyInteger oCount)
		throws PsyError
	{
		long count=oCount.longValue();
		if(count<0)
			throw new PsyRangeCheck();
		if(count*length()>Integer.MAX_VALUE)
			throw new PsyLimitCheck();
		final PsyFormalArray<T> oResult=(PsyFormalArray<T>)psyNewEmpty();
		while(count-->0)
			oResult.psyAppendAll(this);
		return oResult;
	}

	default public void psyPutInterval(final PsyInteger oIndex, final PsyIterable<? extends T> oEnumeration)
		throws PsyError
	{
		int index=oIndex.intValue();
		if(index<0
			||
			oEnumeration instanceof PsyLengthy
			&& index+((PsyLengthy)oEnumeration).length()>=length())
			throw new PsyRangeCheck();
		for(final T o: oEnumeration)
		{
			put((int)index++, o);
			if(index==length())
				break;
		}
	}

	@Override
	default public void psyDelete(final PsyInteger oIndex)
		throws PsyError
	{
		delete(oIndex.intValue());
	}

	public void delete(int index)
		throws PsyError;

	@Override
	default public T psyExtract(final PsyInteger oIndex)
		throws PsyError
	{
		return extract(oIndex.intValue());
	}

	@Override
	default public PsyFormalArray<T> psyGetAll(final PsyIterable<PsyInteger> oIndices)
		throws PsyError
	{
		final PsyFormalArray<T> oResult=(PsyFormalArray<T>)psyNewEmpty();
		for(final PsyInteger oIndex: oIndices)
			oResult.psyAppend(psyGet(oIndex));
		return oResult;
	}

	public void psySetLength(final PsyInteger oLength)
		throws PsyError;

	public T extract(final int index)
		throws PsyError;

	public PsyFormalArray<T> psyExtractInterval(final PsyInteger oIndex, final PsyInteger oCount)
		throws PsyError;

	@Override
	public PsyFormalArray<T> psySlice(final PsyIterable<PsyInteger> oIndices)
		throws PsyError;

	@Override
	default public PsyStream psyKeys()
	{
		return new PsyStream(java.util.stream.IntStream.range(0, length()).mapToObj(PsyInteger::of));
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
		/*
		return new PsyFormalStream<PsyObject>()
			{
				@Override
				public void psyForAll(final PsyObject oProc)
					throws PsyError
				{
					final var interpreter=PsyContext.psyCurrentContext();
					final var ostack=interpreter.operandStack();
					final java.util.Iterator<PsyInteger> iterator=psyKeys().iterator();
					interpreter.pushLoopLevel();
					interpreter.executionStack().push(new PsyOperator("#forall_continue")
						{
							@Override
							public void action()
								throws PsyError
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
												PsyInteger.of(index++): parentIterator.next();
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
