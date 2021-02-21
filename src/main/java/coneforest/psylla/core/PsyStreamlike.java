package coneforest.psylla.core;
import coneforest.psylla.*;

@Type("streamlike")
public interface PsyStreamlike<T extends PsyObject>
	extends
		PsyStreamable<T>,
		PsyCloseable
{
	@Override
	default public PsyStreamlike<T> psyStream()
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

	default public PsyStreamlike<T> psyConcat(final PsyStreamlike<T> oStreamlike)
	{
		return new PsyStreamlike<T>()
			{
				@Override
				public java.util.stream.Stream<T> stream()
				{
					return java.util.stream.Stream.concat(PsyStreamlike.this.stream(), oStreamlike.stream());
				}
			};
	}

	default public PsyStreamlike<PsyObject> psyMapped(final PsyExecutable oMapper)
		throws PsyException
	{
		return new PsyStreamlike<PsyObject>()
			{
				@Override
				public java.util.stream.Stream<PsyObject> stream()
				{
					return PsyStreamlike.this.stream().map(oMapper.<T, PsyObject>asFunction());
				}
			};
	}

	default public PsyStreamlike<T> psySorted(final PsyExecutable oComparator)
	{
		return new PsyStreamlike<T>()
			{
				@Override
				public java.util.stream.Stream<T> stream()
				{
					return PsyStreamlike.this.stream().sorted(oComparator.<T>asComparator());
				}
			};
	}

	default public PsyStreamlike<T> psySkipped(final PsyInteger oCount)
		throws PsyRangeCheckException
	{
		final long count=oCount.longValue();
		if(count<0)
			throw new PsyRangeCheckException();
		return new PsyStreamlike<T>()
			{
				@Override
				public java.util.stream.Stream<T> stream()
				{
					return PsyStreamlike.this.stream().skip(count);
				}
			};
	}

	default public PsyStreamlike<T> psyLimited(final PsyInteger oCount)
		throws PsyRangeCheckException
	{
		final long count=oCount.longValue();
		if(count<0)
			throw new PsyRangeCheckException();
		return new PsyStreamlike<T>()
			{
				@Override
				public java.util.stream.Stream<T> stream()
				{
					return PsyStreamlike.this.stream().limit(count);
				}
			};
	}
	/**
	*	Returns a Ψ-{@code streamlike} over elements of this object that
	*	satisfies the criterium calculated during Ψ-{@code proc} invocation.
	*
	*	@param oPredicate a procedure
	*	@return an iterable
	*	@throws PsyException
	*/
	default public PsyStreamlike<T> psyFiltered(final PsyExecutable oPredicate)
		throws PsyException
	{
		return new PsyStreamlike<T>()
			{
				@Override
				public java.util.stream.Stream<T> stream()
				{
					return PsyStreamlike.this.stream().filter(oPredicate.<T>asPredicate());
				}
			};
	}

	/*
	default public PsyStreamlike<T> psyIterate(final T oSeed, final PsyProc oProc)
	{
		final var interpreter=(Interpreter)PsyContext.psyCurrentContext();
		final var ostack=interpreter.operandStack();
		final var op=oProc.<T>asUnaryOperator(interpreter);
		return new PsyStreamlike<T>()
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
				public void action()
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
						PsyContext.psyCurrentContext().executionStack().push(this);
						oProc.invoke();
					}
					else
						PsyContext.psyCurrentContext().popLoopLevel();
				}
			});
	}

	default public T psyReduce(final T oIdentity, final PsyExecutable oAccumulator)
	{
		T oIdent=oIdentity;
		final var iterator=stream().iterator();
		final var op=oAccumulator.<T>asBinaryOperator();
		while(iterator.hasNext())
			oIdent=op.apply(oIdent, iterator.next());
		return oIdent;
	}

	public java.util.stream.Stream<T> stream();

}
