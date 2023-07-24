package coneforest.psylla.core;

import coneforest.psylla.*;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

/**
*	A representation of {@code formalarray}, an abstraction of an array composed of {@code object}s.
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
		throws PsyRangeCheckException
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
		throws PsyRangeCheckException;

	@Override
	default public T psyGet(final PsyInteger oIndex)
		throws PsyRangeCheckException
	{
		return get(oIndex.intValue());
	}

	public PsyFormalArray<T> psyGetInterval(final PsyInteger oIndex, final PsyInteger oLength)
		throws PsyRangeCheckException;

	public void put(final int index, final T o)
		throws PsyRangeCheckException;

	@Override
	default public void psyPut(final PsyInteger oIndex, final T o)
		throws PsyRangeCheckException
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
	*	@throws PsyRangeCheckException when TODO.
	*/
	public void insert(final int index, final T o)
		throws PsyRangeCheckException;

	/**
	*	Inserts the specified {@code object} into this array at the position
	*	specified by a given {@code integer} index.
	*
	*	@param oIndex an {@code integer} index.
	*	@param o an {@code object}.
	*
	*	@throws PsyRangeCheckException when TODO.
	*/
	default public void psyInsert(final PsyInteger oIndex, final T o)
		throws PsyRangeCheckException
	{
		insert(oIndex.intValue(), o);
	}

	default public void psyInsertAll(final PsyInteger oIndex, final PsyIterable<? extends T> oEnumeration)
		throws PsyRangeCheckException
	{
		int index=oIndex.intValue();
		for(final T o: (this!=oEnumeration? oEnumeration: (PsyIterable<? extends T>)psyClone()))
			insert(index++, o);
	}

	/**
	*	Inserts the specified {@code object} into this array at the beginning.
	*
	*	@param o an {@code object}.
	*	@throws PsyRangeCheckException when TODO.
	*/
	default public void psyPrepend(final T o)
		throws PsyRangeCheckException
	{
		insert(0, o);
	}

	default public T psyPreChop()
		throws PsyRangeCheckException
	{
		return extract(0);
	}

	default public T psyPostChop()
		throws PsyRangeCheckException
	{
		return extract(length()-1);
	}

	default public void psyPrependAll(final PsyIterable<? extends T> oEnumeration)
		throws PsyRangeCheckException
	{
		psyInsertAll(PsyInteger.ZERO, oEnumeration);
	}

	@Override
	default public PsyFormalArray<T> psyReplicate(final PsyInteger oCount)
		throws
			PsyLimitCheckException,
			PsyRangeCheckException,
			PsyUnsupportedException
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
		throws PsyRangeCheckException
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
		throws PsyRangeCheckException
	{
		delete(oIndex.intValue());
	}

	public void delete(int index)
		throws PsyRangeCheckException;

	@Override
	default public T psyExtract(final PsyInteger oIndex)
		throws PsyRangeCheckException
	{
		return extract(oIndex.intValue());
	}

	@Override
	default public PsyFormalArray<T> psyGetAll(final PsyIterable<PsyInteger> oIndices)
		throws
			PsyRangeCheckException,
			PsyLimitCheckException,
			PsyUnsupportedException
	{
		final PsyFormalArray<T> oResult=(PsyFormalArray<T>)psyNewEmpty();
		for(final PsyInteger oIndex: oIndices)
			oResult.psyAppend(psyGet(oIndex));
		return oResult;
	}

	public void psySetLength(final PsyInteger oLength)
		throws PsyLimitCheckException, PsyRangeCheckException;

	public T extract(final int index)
		throws PsyRangeCheckException;

	public PsyFormalArray<T> psyExtractInterval(final PsyInteger oIndex, final PsyInteger oCount)
		throws PsyRangeCheckException;

	@Override
	public PsyFormalArray<T> psySlice(final PsyIterable<PsyInteger> oIndices)
		throws
			PsyRangeCheckException,
			PsyLimitCheckException,
			PsyUnsupportedException;

	@Override
	default public PsyStream psyKeys()
	{
		return new PsyStream(IntStream.range(0, length()).mapToObj(PsyInteger::of));
	}

	@Override
	default public PsyFormalStream<T> psyValues()
	{
		return new PsyStream(StreamSupport.stream(spliterator(), false));
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
					throws PsyErrorException
				{
					final var interpreter=PsyContext.psyCurrentContext();
					final var ostack=interpreter.operandStack();
					final java.util.Iterator<PsyInteger> iterator=psyKeys().iterator();
					interpreter.pushLoopLevel();
					interpreter.executionStack().push(new PsyOperator("#forall_continue")
						{
							@Override
							public void action()
								throws PsyErrorException
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
		return toSyntaxStringHelper(new HashSet<PsyContainer<T>>());
	}

	@Override
	default public String toSyntaxStringHelper(final Set<PsyContainer<T>> processed)
	{
		if(!processed.add((PsyContainer<T>)this))
			return '%'+typeName()+'%';
		final var sj=new StringJoiner(" ", "[", "]");
		for(final var o: this)
			sj.add(o instanceof PsyContainer?
				((PsyContainer<T>)o).toSyntaxStringHelper(processed):
				o.toSyntaxString());
		return sj.toString();
	}

	/**
	*	Context action of the {@code extractinterval} operator.
	*/
	@OperatorType("extractinterval")
	public static final ContextAction PSY_EXTRACTINTERVAL
		=ContextAction.<PsyFormalArray, PsyInteger, PsyInteger>ofTriFunction(PsyFormalArray::psyExtractInterval);

	/**
	*	Context action of the {@code getinterval} operator.
	*/
	@OperatorType("getinterval")
	public static final ContextAction PSY_GETINTERVAL
		=ContextAction.<PsyFormalArray, PsyInteger, PsyInteger>ofTriFunction(PsyFormalArray::psyGetInterval);

	/**
	*	Context action of the {@code insert} operator.
	*/
	@OperatorType("insert")
	public static final ContextAction PSY_INSERT
		=ContextAction.<PsyFormalArray, PsyInteger, PsyObject>ofTriConsumer(PsyFormalArray::psyInsert);

	/**
	*	Context action of the {@code insertall} operator.
	*/
	@OperatorType("insertall")
	public static final ContextAction PSY_INSERTALL
		=ContextAction.<PsyFormalArray, PsyInteger, PsyIterable>ofTriConsumer(PsyFormalArray::psyInsertAll);

	/**
	*	Context action of the {@code postchop} operator.
	*/
	@OperatorType("postchop")
	public static final ContextAction PSY_POSTCHOP
		=ContextAction.<PsyFormalArray>ofFunction(PsyFormalArray::psyPostChop);

	/**
	*	Context action of the {@code prechop} operator.
	*/
	@OperatorType("prechop")
	public static final ContextAction PSY_PRECHOP
		=ContextAction.<PsyFormalArray>ofFunction(PsyFormalArray::psyPreChop);

	/**
	*	Context action of the {@code prepend} operator.
	*/
	@OperatorType("prepend")
	public static final ContextAction PSY_PREPEND
		=ContextAction.<PsyFormalArray, PsyObject>ofBiConsumer(PsyFormalArray::psyPrepend);

	/**
	*	Context action of the {@code prependall} operator.
	*/
	@OperatorType("prependall")
	public static final ContextAction PSY_PREPENDALL
		=ContextAction.<PsyFormalArray, PsyIterable>ofBiConsumer(PsyFormalArray::psyPrependAll);

	/**
	*	Context action of the {@code putinterval} operator.
	*/
	@OperatorType("putinterval")
	public static final ContextAction PSY_PUTINTERVAL
		=ContextAction.<PsyFormalArray, PsyInteger, PsyIterable>ofTriConsumer(PsyFormalArray::psyPutInterval);

	/**
	*	Context action of the {@code reverse} operator.
	*/
	@OperatorType("reverse")
	public static final ContextAction PSY_REVERSE
		=ContextAction.<PsyFormalArray>ofFunction(PsyFormalArray::psyReverse);

	/**
	*	Context action of the {@code setlength} operator.
	*/
	@OperatorType("setlength")
	public static final ContextAction PSY_SETLENGTH
		=ContextAction.<PsyFormalArray, PsyInteger>ofBiConsumer(PsyFormalArray::psySetLength);
}
