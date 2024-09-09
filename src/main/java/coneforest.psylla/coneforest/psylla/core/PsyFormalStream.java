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
		PsyCloseable
{
	@Override
	default public PsyFormalStream<T> psyStream()
	{
		return this;
	}

	/**
	*	{@return the count of elements in this {@code formalstream}}
	*
	*	@throws PsyInvalidStateException TODO
	*/
	default public PsyInteger psyCount()
		throws PsyInvalidStateException
	{
		try
		{
			return PsyInteger.of(stream().count());
		}
		/*catch(final RuntimeException ex)
		{
			System.out.println("ERROR!");
			return null;
		}*/
		catch(final IllegalStateException ex)
		{
			throw new PsyInvalidStateException();
		}
	}

	/**
	*	Closes this {@code formalstream}.
	*/
	default public void psyClose()
	{
		stream().close();
	}

	default public PsyFormalStream<T> psyConcat(final PsyFormalStream<T> oStream)
	{
		return new PsyFormalStream<T>()
			{
				@Override
				public Stream<T> stream()
				{
					return Stream.concat(PsyFormalStream.this.stream(), oStream.stream());
				}
			};
	}

	default public PsyFormalStream<PsyObject> psyMapped(final PsyExecutable oMapper, final PsyContext oContext)
		throws PsyErrorException
	{
		return new PsyFormalStream<PsyObject>()
			{
				@Override
				public Stream<PsyObject> stream()
				{
					return PsyFormalStream.this.stream().map(oMapper.<T, PsyObject>asFunction(oContext));
				}
			};
	}

	default public PsyFormalStream<T> psySorted(final PsyExecutable oComparator, final PsyContext oContext)
	{
		return new PsyFormalStream<T>()
			{
				@Override
				public Stream<T> stream()
				{
					return PsyFormalStream.this.stream().sorted(oComparator.<T>asComparator(oContext));
				}
			};
	}

	/**
	*	{@return a {@code formalstream} consisting of the remaining elements of this {@code
	*	formalstream} after discarding the first oCount elements of the stream}
	*
	*	@param oCount the number of leading elements to skip.
	*	@throws PsyRangeCheckException when oCount is negative.
	*/
	default public PsyFormalStream<T> psySkipped(final PsyInteger oCount)
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
	*	truncated to be no longer than oCount in length}
	*
	*	@param oCount the number of elements the stream should be limited to.
	*	@throws PsyRangeCheckException when oCount is negative.
	*/
	default public PsyFormalStream<T> psyLimited(final PsyInteger oCount)
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
					return PsyFormalStream.this.stream().limit(count);
				}
			};
	}

	default public PsyFormalStream<T> psyPeeked(final PsyExecutable oProc, final PsyContext oContext)
		throws PsyRangeCheckException
	{
		return new PsyFormalStream<T>()
			{
				@Override
				public Stream<T> stream()
				{
					return PsyFormalStream.this.stream().peek(oProc.asConsumer(oContext));
				}
			};
	}

	/**
	*	{@return a stream over elements of this stream that satisfies the given predicate}
	*
	*	@param oPredicate a predicate.
	*	@param oContext a context in which a predicate is called.
	*	@throws PsyErrorException TODO
	*/
	default public PsyFormalStream<T> psyFiltered(final PsyExecutable oPredicate, final PsyContext oContext)
		throws PsyErrorException
	{
		return new PsyFormalStream<T>()
			{
				@Override
				public Stream<T> stream()
				{
					return PsyFormalStream.this.stream().filter(oPredicate.<T>asPredicate(oContext));
				}
			};
	}

	default public void psyForAll(final PsyObject oProc, final PsyContext oContext)
		throws PsyErrorException
	{
		final var ostack=oContext.operandStack();
		try
		{
			final java.util.Iterator<T> iterator=stream().iterator();
			oContext.pushLoopLevel();
			oContext.executionStack().push(new PsyOperator("#forall_continue")
				{
					@Override
					public void perform(final PsyContext oContext1)
						throws PsyErrorException
					{
						if(iterator.hasNext())
						{
							try
							{
								ostack.push(iterator.next());
							}
							catch(final NoSuchElementException ex)
							{
								// TODO more suitable exception type
								throw new PsyUndefinedException();
							}
							oContext1.executionStack().push(this);
							oProc.invoke(oContext1);
						}
						else
							oContext1.popLoopLevel();
					}
				});
		}
		catch(final IllegalStateException ex)
		{
			throw new PsyInvalidStateException();
		}
	}

	default public T psyReduce(final T oIdentity, final PsyExecutable oAccumulator, final PsyContext oContext)
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

	default public PsyFormalStream<T> psyDistinct()
	{
		return new PsyFormalStream<T>()
			{
				@Override
				public Stream<T> stream()
				{
					return PsyFormalStream.this.stream().distinct();
				}
			};
	}

	public Stream<T> stream();

	/**
	*	Context action of the {@code count} operator.
	*/
	@OperatorType("count")
	public static final ContextAction PSY_COUNT
		=ContextAction.<PsyFormalStream>ofFunction(PsyFormalStream::psyCount);

	/**
	*	Context action of the {@code distinct} operator.
	*/
	@OperatorType("distinct")
	public static final ContextAction PSY_DISTINCT
		=ContextAction.<PsyFormalStream>ofFunction(PsyFormalStream::psyDistinct);

	/**
	*	Context action of the {@code filtered} operator.
	*/
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
	@OperatorType("limited")
	public static final ContextAction PSY_LIMITED
		=ContextAction.<PsyFormalStream, PsyInteger>ofBiFunction(PsyFormalStream::psyLimited);

	/**
	*	Context action of the {@code mapped} operator.
	*/
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
	@OperatorType("skipped")
	public static final ContextAction PSY_SKIPPED
		=ContextAction.<PsyFormalStream, PsyInteger>ofBiFunction(PsyFormalStream::psySkipped);

	/**
	*	Context action of the {@code concat} operator.
	*/
	@OperatorType("concat")
	public static final ContextAction PSY_CONCAT
		=ContextAction.<PsyFormalStream, PsyFormalStream>ofBiFunction(PsyFormalStream::psyConcat);

	/**
	*	Context action of the {@code sorted} operator.
	*/
	@OperatorType("sorted")
	public static final ContextAction PSY_SORTED=oContext->
		{
			final var ostack=oContext.operandStackBacked(2);
			ostack.push(ostack.<PsyFormalStream>getBacked(0).psySorted(ostack.getBacked(1), oContext));
		};
}
