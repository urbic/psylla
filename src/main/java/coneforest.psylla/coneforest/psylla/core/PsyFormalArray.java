package coneforest.psylla.core;

import coneforest.psylla.runtime.*;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

/**
*	The representation of {@code formalarray}, an abstraction of an array composed of {@code
*	object}s.
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
	/**
	*	Context action of the {@code extractinterval} operator.
	*/
	@SuppressWarnings("rawtypes")
	@OperatorType("extractinterval")
	public static final ContextAction PSY_EXTRACTINTERVAL
		=ContextAction.<PsyFormalArray, PsyInteger, PsyInteger>ofTriFunction(PsyFormalArray::psyExtractInterval);

	/**
	*	Context action of the {@code getinterval} operator.
	*/
	@SuppressWarnings("rawtypes")
	@OperatorType("getinterval")
	public static final ContextAction PSY_GETINTERVAL
		=ContextAction.<PsyFormalArray, PsyInteger, PsyInteger>ofTriFunction(PsyFormalArray::psyGetInterval);

	/**
	*	Context action of the {@code insert} operator.
	*/
	@SuppressWarnings({"unchecked", "rawtypes"})
	@OperatorType("insert")
	public static final ContextAction PSY_INSERT
		=ContextAction.<PsyFormalArray, PsyInteger, PsyObject>ofTriConsumer(PsyFormalArray::psyInsert);

	/**
	*	Context action of the {@code insertall} operator.
	*/
	@SuppressWarnings({"unchecked", "rawtypes"})
	@OperatorType("insertall")
	public static final ContextAction PSY_INSERTALL
		=ContextAction.<PsyFormalArray, PsyInteger, PsyIterable>ofTriConsumer(PsyFormalArray::psyInsertAll);

	/**
	*	Context action of the {@code postchop} operator.
	*/
	@SuppressWarnings("rawtypes")
	@OperatorType("postchop")
	public static final ContextAction PSY_POSTCHOP
		=ContextAction.<PsyFormalArray>ofFunction(PsyFormalArray::psyPostChop);

	/**
	*	Context action of the {@code prechop} operator.
	*/
	@SuppressWarnings("rawtypes")
	@OperatorType("prechop")
	public static final ContextAction PSY_PRECHOP
		=ContextAction.<PsyFormalArray>ofFunction(PsyFormalArray::psyPreChop);

	/**
	*	Context action of the {@code prepend} operator.
	*/
	@SuppressWarnings({"unchecked", "rawtypes"})
	@OperatorType("prepend")
	public static final ContextAction PSY_PREPEND
		=ContextAction.<PsyFormalArray, PsyObject>ofBiConsumer(PsyFormalArray::psyPrepend);

	/**
	*	Context action of the {@code prependall} operator.
	*/
	@SuppressWarnings({"unchecked", "rawtypes"})
	@OperatorType("prependall")
	public static final ContextAction PSY_PREPENDALL
		=ContextAction.<PsyFormalArray, PsyIterable>ofBiConsumer(PsyFormalArray::psyPrependAll);

	/**
	*	Context action of the {@code putinterval} operator.
	*/
	@SuppressWarnings({"unchecked", "rawtypes"})
	@OperatorType("putinterval")
	public static final ContextAction PSY_PUTINTERVAL
		=ContextAction.<PsyFormalArray, PsyInteger, PsyIterable>ofTriConsumer(PsyFormalArray::psyPutInterval);

	/**
	*	Context action of the {@code reverse} operator.
	*/
	@SuppressWarnings("rawtypes")
	@OperatorType("reverse")
	public static final ContextAction PSY_REVERSE
		=ContextAction.<PsyFormalArray>ofFunction(PsyFormalArray::psyReverse);

	/**
	*	Context action of the {@code setlength} operator.
	*/
	@SuppressWarnings("rawtypes")
	@OperatorType("setlength")
	public static final ContextAction PSY_SETLENGTH
		=ContextAction.<PsyFormalArray, PsyInteger>ofBiConsumer(PsyFormalArray::psySetLength);

	@Override
	public PsyFormalArray<T> psyClone();

	public default PsyFormalArray<T> psyReverse()
		throws PsyRangeCheckException
	{
		final var oCloned=psyClone();
		final int length=oCloned.length();
		for(int i=0; i<length/2; i++)
		{
			final T o=oCloned.get(i);
			oCloned.put(i, oCloned.get(length-1-i));
			oCloned.put(length-1-i, o);
		}
		return oCloned;
	}

	@Override
	public default PsyBoolean psyKnown(final PsyInteger oIndex)
	{
		// TODO: index<0
		final long index=oIndex.longValue();
		return PsyBoolean.of(index>=0 && index<length());
	}

	/**
	*	Returns the element at the specified position in this array.
	*
	*	@param index the index of the element.
	*	@return the element at the specified position in this list.
	*	@throws PsyRangeCheckException if the index is out of range.
	*/
	public T get(final int index)
		throws PsyRangeCheckException;

	@Override
	public default T psyGet(final PsyInteger oIndex)
		throws PsyRangeCheckException
	{
		return get(oIndex.intValue());
	}

	public PsyFormalArray<T> psyGetInterval(final PsyInteger oIndex, final PsyInteger oLength)
		throws PsyRangeCheckException;

	public void put(final int index, final T o)
		throws PsyRangeCheckException;

	@Override
	public default void psyPut(final PsyInteger oIndex, final T o)
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
	*	@throws PsyLimitCheckException when TODO.
	*	@throws PsyRangeCheckException when TODO.
	*/
	public void insert(final int index, final T o)
		throws PsyRangeCheckException, PsyLimitCheckException;

	/**
	*	Inserts the specified {@code object} into this array at the position
	*	specified by a given {@code integer} index.
	*
	*	@param oIndex an {@code integer} index.
	*	@param o an {@code object}.
	*
	*	@throws PsyLimitCheckException when TODO.
	*	@throws PsyRangeCheckException when TODO.
	*/
	public default void psyInsert(final PsyInteger oIndex, final T o)
		throws PsyRangeCheckException, PsyLimitCheckException
	{
		insert(oIndex.intValue(), o);
	}

	public default void psyInsertAll(final PsyInteger oIndex, final PsyIterable<? extends T> oEnumeration)
		throws PsyRangeCheckException, PsyLimitCheckException
	{
		int index=oIndex.intValue();
		for(final T o: this!=oEnumeration? oEnumeration: (PsyIterable<? extends T>)psyClone())
			insert(index++, o);
	}

	/**
	*	Inserts the specified {@code object} into this array at the beginning.
	*
	*	@param o an {@code object}.
	*	@throws PsyRangeCheckException when TODO.
	*/
	public default void psyPrepend(final T o)
		throws PsyRangeCheckException, PsyLimitCheckException
	{
		insert(0, o);
	}

	public default T psyPreChop()
		throws PsyRangeCheckException
	{
		return extract(0);
	}

	public default T psyPostChop()
		throws PsyRangeCheckException
	{
		return extract(length()-1);
	}

	public default void psyPrependAll(final PsyIterable<? extends T> oEnumeration)
		throws PsyRangeCheckException, PsyLimitCheckException
	{
		psyInsertAll(PsyInteger.ZERO, oEnumeration);
	}

	@Override
	public default PsyFormalArray<T> psyReplicate(final PsyInteger oCount)
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

	public default void psyPutInterval(final PsyInteger oIndex, final PsyIterable<? extends T> oEnumeration)
		throws PsyRangeCheckException
	{
		int index=oIndex.intValue();
		if(index<0 || oEnumeration instanceof PsyLengthy oLengthy && index+oLengthy.length()>=length())
			throw new PsyRangeCheckException();
		for(final var o: oEnumeration)
		{
			put(index++, o);
			if(index==length())
				break;
		}
	}

	@Override
	public default void psyDelete(final PsyInteger oIndex)
		throws PsyRangeCheckException
	{
		delete(oIndex.intValue());
	}

	/**
	*	Removes the element at the specified position in this array.
	*
	*	@param index the index of the element to be removed.
	*	@throws PsyRangeCheckException if the index is out of range.
	*/
	public void delete(int index)
		throws PsyRangeCheckException;

	@Override
	public default T psyExtract(final PsyInteger oIndex)
		throws PsyRangeCheckException
	{
		return extract(oIndex.intValue());
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
	public default PsyFormalStream<PsyInteger> psyKeys()
	{
		//return new PsyStream(IntStream.range(0, length()).<PsyInteger>mapToObj(PsyInteger::of));
		return PsyFormalStream.<PsyInteger>of(
				IntStream.range(0, length()).<PsyInteger>mapToObj(PsyInteger::of));
	}

	@Override
	public default PsyFormalStream<T> psyValues()
	{
		return PsyFormalStream.<T>of(StreamSupport.<T>stream(spliterator(), false));
	}

	/*
	@Override
	public default PsyFormalArray<T> psyGetAll(final PsyIterable<PsyInteger> oIndices)
		throws
			PsyRangeCheckException,
			PsyLimitCheckException,
			PsyUnsupportedException
	{
		final PsyFormalArray<T> oResult=(PsyFormalArray<T>)psyNewEmpty();
		for(final var oIndex: oIndices)
			oResult.psyAppend(psyGet(oIndex));
		return oResult;
	}
	*/

	@Override
	public default PsyFormalStream<PsyObject> psyEntries()
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
					final Iterator<PsyInteger> iterator=psyKeys().iterator();
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
									final var oIndex=iterator.next();
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
				public Stream<PsyObject> stream()
				{
					return new Stream<PsyObject>()
						{

							@Override
							public Iterator<PsyObject> iterator()
							{
								return new Iterator<PsyObject>()
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

										private final Iterator<PsyObject> parentIterator
											=(Iterator<PsyObject>)PsyFormalArray.this.iterator();

									};
							}
						};
				}
			};
		*/
	}

	@Override
	public default String toSyntaxString()
	{
		return toSyntaxStringHelper(new HashSet<PsyContainer<? extends PsyObject>>());
	}

	@Override
	public default String toSyntaxStringHelper(final Set<PsyContainer<? extends PsyObject>> processed)
	{
		if(!processed.add(this))
			return '%'+typeName()+'%';
		final var sj=new StringJoiner(" ", "[", "]");
		for(final var o: this)
			sj.add(o instanceof PsyContainer<? extends PsyObject> oContainer?
				oContainer.toSyntaxStringHelper(processed):
				o.toSyntaxString());
		return sj.toString();
	}
}
