package coneforest.psylla.core;
import coneforest.psylla.*;

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
	{
		return PsyInteger.valueOf(stream().count());
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
				public java.util.stream.Stream<T> stream()
				{
					return java.util.stream.Stream.concat(PsyFormalStream.this.stream(), oStream.stream());
				}
			};
	}

	default public PsyFormalStream<PsyObject> psyMapped(final PsyExecutable oMapper, final PsyContext oContext)
		throws PsyException
	{
		return new PsyFormalStream<PsyObject>()
			{
				@Override
				public java.util.stream.Stream<PsyObject> stream()
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
				public java.util.stream.Stream<T> stream()
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
				public java.util.stream.Stream<T> stream()
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
				public java.util.stream.Stream<T> stream()
				{
					return PsyFormalStream.this.stream().limit(count);
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
	*	@throws PsyException
	*/
	default public PsyFormalStream<T> psyFiltered(final PsyExecutable oPredicate, final PsyContext oContext)
		throws PsyException
	{
		return new PsyFormalStream<T>()
			{
				@Override
				public java.util.stream.Stream<T> stream()
				{
					return PsyFormalStream.this.stream().filter(oPredicate.<T>asPredicate(oContext));
				}
			};
	}

	/*
	default public PsyFormalStream<T> psyIterate(final T oSeed, final PsyProc oProc)
	{
		final var interpreter=(Interpreter)PsyContext.psyCurrentContext();
		final var ostack=interpreter.operandStack();
		final var op=oProc.<T>asUnaryOperator(interpreter);
		return new PsyFormalStream<T>()
			{
				@Override
				public java.util.Iterator<T> iterator()
				{
					return new java.util.Iterator<T>()
						{
							@Override
							public boolean hasNext()
							{
								return true;
							}

							@Override
							public T next()
							{
								final T oSeed=oSeed1;
								oSeed1=op.apply(oSeed);
								return oSeed;
							}

							private T oSeed1=oSeed;
						};
				}
			};
	}
	*/

	default public void psyForAll(final PsyObject oProc, final PsyContext oContext)
		throws PsyException
	{
		final var ostack=oContext.operandStack();
		final java.util.Iterator<T> iterator=stream().iterator();
		oContext.pushLoopLevel();
		oContext.executionStack().push(new PsyOperator("#forall_continue")
			{
				@Override
				public void action(final PsyContext oContext1)
					throws PsyException
				{
					if(iterator.hasNext())
					{
						try
						{
							ostack.push(iterator.next());
						}
						catch(final java.util.NoSuchElementException e)
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

	default public T psyReduce(final T oIdentity, final PsyExecutable oAccumulator, final PsyContext oContext)
		throws PsyException
	{
		//try
		//{
			return stream().reduce(oIdentity, oAccumulator.<T>asBinaryOperator(oContext));
		//}
		//catch(final Exception e)
		//{
		//	throw new PsyException();
		//	TODO
		//}
	}

	default public PsyFormalStream<T> psyDistinct()
	{
		return new PsyFormalStream<T>()
			{
				@Override
				public java.util.stream.Stream<T> stream()
				{
					return PsyFormalStream.this.stream().distinct();
				}
			};
	}

	public java.util.stream.Stream<T> stream();

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
						ostack.push(ostack.<PsyFormalStream>getBacked(0).psyFiltered(ostack.getBacked(1), oContext));
					}),
			new PsyOperator.Arity21<PsyFormalStream, PsyInteger>
				("limited", PsyFormalStream::psyLimited),
			new PsyOperator.Action
				("mapped",
					(oContext)->
					{
						final var ostack=oContext.operandStackBacked(2);
						ostack.push(ostack.<PsyFormalStream>getBacked(0).psyMapped(ostack.getBacked(1), oContext));
					}),
			new PsyOperator.Action
				("reduce",
					(oContext)->
					{
						final var ostack=oContext.operandStackBacked(3);
						ostack.push(ostack.<PsyFormalStream>getBacked(0).psyReduce(ostack.getBacked(1), ostack.getBacked(2), oContext));
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
