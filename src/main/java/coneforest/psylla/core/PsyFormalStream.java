package coneforest.psylla.core;
import coneforest.psylla.*;
import java.util.stream.Stream;

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
	*	Returns the count of elements in this {@code formalstream}.
	*
	*	@return a count
	*/
	default public PsyInteger psyCount()
		throws PsyInvalidStateException
	{
		try
		{
			return PsyInteger.valueOf(stream().count());
		}
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
	*	Returns a {@code formalstream} consisting of the remaining elements of
	*	this {@code formalstream} after discarding the first oCount elements of
	*	the stream.
	*
	*	@param oCount the number of leading elements to skip
	*	@return a skipped stream
	*	@throws PsyRangeCheckException when oCount is negative
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
	*	Returns a {@code formalstream} consisting of the elements of this
	*	{@code formalstream}, truncated to be no longer than oCount in length.
	*
	*	@param oCount the number of elements the stream should be limited to
	*	@return a limited stream
	*	@throws PsyRangeCheckException when oCount is negative
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
	*	Returns a stream over elements of this stream that satisfies the given
	*	predicate.
	*
	*	@param oPredicate a predicate
	*	@param oContext a context in which a predicate is called
	*	@return a filtered stream
	*	@throws PsyErrorException
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
					public void action(final PsyContext oContext1)
						throws PsyErrorException
					{
						if(iterator.hasNext())
						{
							try
							{
								ostack.push(iterator.next());
							}
							catch(final java.util.NoSuchElementException ex)
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
		throws PsyErrorException
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

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity11<PsyFormalStream>
				("count", PsyFormalStream::psyCount),
			new PsyOperator.Arity11<PsyFormalStream>
				("distinct", PsyFormalStream::psyDistinct),
			new PsyOperator.Action
				("filtered",
					(oContext)->
					{
						final var ostack=oContext.operandStackBacked(2);
						ostack.push(ostack.<PsyFormalStream>getBacked(0).psyFiltered(
								ostack.getBacked(1), oContext));
					}),
			new PsyOperator.Arity21<PsyFormalStream, PsyInteger>
				("limited", PsyFormalStream::psyLimited),
			new PsyOperator.Action
				("mapped",
					(oContext)->
					{
						final var ostack=oContext.operandStackBacked(2);
						ostack.push(ostack.<PsyFormalStream>getBacked(0).psyMapped(
								ostack.getBacked(1), oContext));
					}),
			new PsyOperator.Action
				("peeked",
					(oContext)->
					{
						final var ostack=oContext.operandStackBacked(2);
						ostack.push(ostack.<PsyFormalStream>getBacked(0).psyPeeked(
								ostack.getBacked(1), oContext));
					}),
			new PsyOperator.Action
				("reduce",
					(oContext)->
					{
						final var ostack=oContext.operandStackBacked(3);
						ostack.push(ostack.<PsyFormalStream>getBacked(0).psyReduce(
								ostack.getBacked(1), ostack.getBacked(2), oContext));
					}),
			new PsyOperator.Arity21<PsyFormalStream, PsyInteger>
				("skipped", PsyFormalStream::psySkipped),
			new PsyOperator.Arity21<PsyFormalStream, PsyFormalStream>
				("concat", PsyFormalStream::psyConcat),
			new PsyOperator.Action
				("sorted",
					(oContext)->
					{
						final var ostack=oContext.operandStackBacked(2);
						ostack.push(ostack.<PsyFormalStream>getBacked(0).psySorted(ostack.getBacked(1), oContext));
					}),
		};
}
