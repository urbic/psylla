package coneforest.psylla.core;

import coneforest.psylla.runtime.*;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

/**
*	The representation of {@code formalstream}, an abstraction of the stream.
*/
@Type("formalstream")
public interface PsyFormalStream<T extends PsyObject>
	extends
		PsyStreamable<T>,
		PsyCloseable,
		PsyConcatenable<PsyFormalStream<T>>
{
	/**
	*	Context action of the {@code count} operator.
	*/
	@SuppressWarnings("rawtypes")
	@OperatorType("count")
	public static final ContextAction PSY_COUNT
		=ContextAction.<PsyFormalStream>ofFunction(PsyFormalStream::psyCount);

	/**
	*	Context action of the {@code distinct} operator.
	*/
	@SuppressWarnings("rawtypes")
	@OperatorType("distinct")
	public static final ContextAction PSY_DISTINCT
		=ContextAction.<PsyFormalStream>ofFunction(PsyFormalStream::psyDistinct);

	/**
	*	Context action of the {@code filtered} operator.
	*/
	@SuppressWarnings("rawtypes")
	@OperatorType("filtered")
	public static final ContextAction PSY_FILTERED=oContext->
		{
			final var ostack=oContext.operandStackBacked(2);
			ostack.push(ostack.<PsyFormalStream>getBacked(0).psyFiltered(
					ostack.getBacked(1), oContext));
		};

	/**
	*	Context action of the {@code limited} operator.
	*/
	@SuppressWarnings("rawtypes")
	@OperatorType("limited")
	public static final ContextAction PSY_LIMITED
		=ContextAction.<PsyFormalStream, PsyInteger>ofBiFunction(PsyFormalStream::psyLimited);

	/**
	*	Context action of the {@code mapped} operator.
	*/
	@SuppressWarnings("rawtypes")
	@OperatorType("mapped")
	public static final ContextAction PSY_MAPPED=oContext->
		{
			final var ostack=oContext.operandStackBacked(2);
			ostack.push(ostack.<PsyFormalStream>getBacked(0).psyMapped(
					ostack.getBacked(1), oContext));
		};

	/**
	*	Context action of the {@code peeked} operator.
	*/
	@SuppressWarnings("rawtypes")
	@OperatorType("peeked")
	public static final ContextAction PSY_PEEKED=oContext->
		{
			final var ostack=oContext.operandStackBacked(2);
			ostack.push(ostack.<PsyFormalStream>getBacked(0).psyPeeked(
					ostack.getBacked(1), oContext));
		};

	/**
	*	Context action of the {@code reduce} operator.
	*/
	@SuppressWarnings({"unchecked", "rawtypes"})
	@OperatorType("reduce")
	public static final ContextAction PSY_REDUCE=oContext->
		{
			final var ostack=oContext.operandStackBacked(3);
			ostack.push(ostack.<PsyFormalStream>getBacked(0).psyReduce(
					ostack.getBacked(1), ostack.getBacked(2), oContext));
		};

	/**
	*	Context action of the {@code skipped} operator.
	*/
	@SuppressWarnings("rawtypes")
	@OperatorType("skipped")
	public static final ContextAction PSY_SKIPPED
		=ContextAction.<PsyFormalStream, PsyInteger>ofBiFunction(PsyFormalStream::psySkipped);

	///**
	//*	Context action of the {@code concat} operator.
	//*/
	//@SuppressWarnings({"unchecked", "rawtypes"})
	//@OperatorType("concat")
	//public static final ContextAction PSY_CONCAT
	//	=ContextAction.<PsyFormalStream, PsyFormalStream>ofBiFunction(PsyFormalStream::psyConcat);

	/**
	*	Context action of the {@code sorted} operator.
	*/
	@SuppressWarnings("rawtypes")
	@OperatorType("sorted")
	public static final ContextAction PSY_SORTED=oContext->
		{
			final var ostack=oContext.operandStackBacked(2);
			ostack.push(ostack.<PsyFormalStream>getBacked(0).psySorted(ostack.getBacked(1), oContext));
		};

	public static <T1 extends PsyObject> PsyFormalStream<T1> of(final Stream<T1> stream)
	{
		return new PsyFormalStream<T1>()
			{
				@Override
				public Stream<T1> stream()
				{
					return stream;
				}
			};
	}

	@Override
	public default PsyFormalStream<T> psyStream()
	{
		return this;
	}

	/**
	*	{@return the count of elements in this {@code formalstream}}
	*
	*	@throws PsyInvalidStateException when this stream is closed.
	*/
	public default PsyInteger psyCount()
		throws PsyInvalidStateException
	{
		try
		{
			return PsyInteger.of(stream().count());
		}
		catch(final IllegalStateException ex)
		{
			throw new PsyInvalidStateException();
		}
	}

	/**
	*	Closes this {@code formalstream}.
	*/
	public default void psyClose()
	{
		stream().close();
	}

	@Override
	public default PsyFormalStream<T> psyConcat(final PsyFormalStream<T> oStream)
		throws PsyInvalidStateException
	{
		try
		{
			return of(Stream.concat(stream(), oStream.stream()));
		}
		catch(final IllegalStateException ex)
		{
			throw new PsyInvalidStateException();
		}
	}

	public default PsyFormalStream<PsyObject> psyMapped(final PsyExecutable oMapper, final PsyContext oContext)
		throws PsyErrorException
	{
		try
		{
			return of(stream().map(oMapper.<T, PsyObject>asFunction(oContext)));
		}
		catch(final IllegalStateException ex)
		{
			throw new PsyInvalidStateException();
		}
	}

	public default PsyFormalStream<T> psySorted(final PsyExecutable oComparator, final PsyContext oContext)
	{
		return of(stream().sorted(oComparator.<T>asComparator(oContext)));
	}

	/**
	*	{@return a {@code formalstream} consisting of the remaining elements of this {@code
	*	formalstream} after discarding the first oCount elements of the stream}
	*
	*	@param oCount the number of leading elements to skip.
	*	@throws PsyRangeCheckException when oCount is negative.
	*/
	public default PsyFormalStream<T> psySkipped(final PsyInteger oCount)
		throws PsyRangeCheckException
	{
		final var count=oCount.longValue();
		if(count<0)
			throw new PsyRangeCheckException();
		return new PsyFormalStream<T>()
			{
				@Override
				public Stream<T> stream()
				{
					return PsyFormalStream.this.stream().skip(count);
				}
			};
	}

	/**
	*	{@return a {@code formalstream} consisting of the elements of this {@code formalstream},
	*	truncated to be no longer than specified count in length}
	*
	*	@param oCount the number of elements the stream should be limited to.
	*	@throws PsyRangeCheckException when oCount is negative.
	*/
	public default PsyFormalStream<T> psyLimited(final PsyInteger oCount)
		throws PsyRangeCheckException, PsyInvalidStateException
	{
		final var count=oCount.longValue();
		try
		{
			return of(stream().limit(count));
		}
		catch(final IllegalArgumentException ex)
		{
			throw new PsyRangeCheckException();
		}
		catch(final IllegalStateException ex)
		{
			throw new PsyInvalidStateException();
		}
	}

	public default PsyFormalStream<T> psyPeeked(final PsyExecutable oProc, final PsyContext oContext)
		throws PsyRangeCheckException, PsyInvalidStateException
	{
		try
		{
			return of(stream().peek(oProc.asConsumer(oContext)));
		}
		catch(final IllegalStateException ex)
		{
			throw new PsyInvalidStateException();
		}
	}

	/**
	*	{@return a stream over elements of this stream that satisfies the given predicate}
	*
	*	@param oPredicate a predicate.
	*	@param oContext a context in which a predicate is called.
	*	@throws PsyErrorException TODO
	*/
	public default PsyFormalStream<T> psyFiltered(final PsyExecutable oPredicate, final PsyContext oContext)
		throws PsyErrorException
	{
		return of(stream().filter(oPredicate.<T>asPredicate(oContext)));
	}

	public default void psyForAll(final PsyObject oProc, final PsyContext oContext)
		throws PsyErrorException
	{
		final var ostack=oContext.operandStack();
		try
		{
			final var iterator=stream().iterator();
			//System.out.println("LL: "+oContext.pushLoopLevel());
			//final var loopLevel=oContext.pushLoopLevel();
			oContext.executionStack().enterLoop();
			oContext.executionStack().push(new PsyOperator("#forall_continue")
				{
					@Override
					public void perform(final PsyContext oContext1)
						throws PsyErrorException
					{
						//System.out.println("STOPPED:"+oContext1.getStopped());
						/*if(oContext1.getStopped())
						{
							//System.out.println("CLL: "+oContext.currentLoopLevel());
							if(oContext.currentLoopLevel()==-1)
								throw new PsyInvalidExitException();	// TODO
							System.out.println("STOPPED, POPLOOPLEVEL");
							oContext.executionStack().setSize(oContext.popLoopLevel());
							return;
						}*/
						if(iterator.hasNext())
						{
							oContext.executionStack().enterLoop();
							try
							{
								ostack.push(iterator.next());
								oContext1.executionStack().push(this);
								oProc.invoke(oContext1);
							}
							catch(final NoSuchElementException ex)
							{
								// TODO more suitable exception type: PsyInternalError
								throw new PsyUndefinedException();
							}
						}
						else
							oContext1.executionStack().exitLoop();
					}
				});
		}
		catch(final IllegalStateException ex)
		{
			throw new PsyInvalidStateException();
		}
	}

	public default T psyReduce(final T oIdentity, final PsyExecutable oAccumulator, final PsyContext oContext)
		throws PsyInvalidStateException
	{
		try
		{
			return stream().reduce(oIdentity, oAccumulator.<T>asBinaryOperator(oContext));
		}
		catch(final IllegalStateException ex)
		{
			throw new PsyInvalidStateException();
		}
	}

	/**
	*	{@return a stream consisting of the distinct elements of this stream}
	*/
	public default PsyFormalStream<T> psyDistinct()
	{
		return of(stream().distinct());
	}

	public Stream<T> stream();
}
