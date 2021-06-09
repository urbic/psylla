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

	/*XXX default public PsyFormalStream<PsyObject> psyMapped(final PsyExecutable oMapper)
		throws PsyException
	{
		return new PsyFormalStream<PsyObject>()
			{
				@Override
				public java.util.stream.Stream<PsyObject> stream()
				{
					return PsyFormalStream.this.stream().map(oMapper.<T, PsyObject>asFunction());
				}
			};
	}*/

	/*XXX default public PsyFormalStream<T> psySorted(final PsyExecutable oComparator)
	{
		return new PsyFormalStream<T>()
			{
				@Override
				public java.util.stream.Stream<T> stream()
				{
					return PsyFormalStream.this.stream().sorted(oComparator.<T>asComparator());
				}
			};
	}*/

	default public PsyFormalStream<T> psySkipped(final PsyInteger oCount)
		throws PsyRangeCheckException
	{
		final long count=oCount.longValue();
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
		final long count=oCount.longValue();
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
	*	@return an iterable
	*	@throws PsyException
	*/
	/*XXX default public PsyFormalStream<T> psyFiltered(final PsyExecutable oPredicate)
		throws PsyException
	{
		return new PsyFormalStream<T>()
			{
				@Override
				public java.util.stream.Stream<T> stream()
				{
					return PsyFormalStream.this.stream().filter(oPredicate.<T>asPredicate());
				}
			};
	}*/

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

	default public void psyForAll(final PsyObject oProc)
		throws PsyException
	{
		final var interpreter=PsyContext.psyCurrentContext();
		final var ostack=interpreter.operandStack();
		final java.util.Iterator<T> iterator=stream().iterator();
		interpreter.pushLoopLevel();
		interpreter.executionStack().push(new PsyOperator("#forall_continue")
			{
				@Override
				public void action(final PsyContext oContext)
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
						oContext.executionStack().push(this);
						oProc.invoke(oContext);
					}
					else
						oContext.popLoopLevel();
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
			//XXX new PsyOperator.Arity21<PsyFormalStream, PsyExecutable>("filtered", PsyFormalStream::psyFiltered),
			new PsyOperator.Arity21<PsyFormalStream, PsyInteger>
				("limited", PsyFormalStream::psyLimited),
			//new PsyOperator.Arity21<PsyFormalStream, PsyExecutable>("mapped", PsyFormalStream::psyMapped),
			//XXX new PsyOperator.Arity31<PsyFormalStream, PsyObject, PsyExecutable>("reduce", PsyFormalStream::psyReduce),
			new PsyOperator.Arity21<PsyFormalStream, PsyInteger>
				("skipped", PsyFormalStream::psySkipped),
			//new PsyOperator.Arity21<PsyFormalStream, PsyExecutable>("sorted", PsyFormalStream::psySorted),
		};

}
