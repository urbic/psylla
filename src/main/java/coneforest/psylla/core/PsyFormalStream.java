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

	default public PsyInteger psyCount()
	{
		return PsyInteger.valueOf(stream().count());
	}

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
	*	Returns a Ψ-{@code formalstream} over elements of this object that
	*	satisfies the criterium calculated during Ψ-{@code proc} invocation.
	*
	*	@param oPredicate a procedure
	*	@param oContext
	*	@return an iterable
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

	/*XXX
	default public T psyReduce(final T oIdentity, final PsyExecutable oAccumulator)
	{
		T oIdent=oIdentity;
		final var iterator=stream().iterator();
		final var op=oAccumulator.<T>asBinaryOperator();
		while(iterator.hasNext())
			oIdent=op.apply(oIdent, iterator.next());
		return oIdent;
	}*/

	public java.util.stream.Stream<T> stream();

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity11<PsyFormalStream>
				("count", PsyFormalStream::psyCount),
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
			//XXX new PsyOperator.Arity31<PsyFormalStream, PsyObject, PsyExecutable>("reduce", PsyFormalStream::psyReduce),
			new PsyOperator.Arity21<PsyFormalStream, PsyInteger>
				("skipped", PsyFormalStream::psySkipped),
			new PsyOperator.Action
				("sorted",
					(oContext)->
					{
						final var ostack=oContext.operandStackBacked(2);
						ostack.push(ostack.<PsyFormalStream>getBacked(0).psySorted(ostack.getBacked(1), oContext));
					}),
		};

}
