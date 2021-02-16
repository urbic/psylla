package coneforest.psylla.core;
import coneforest.psylla.*;

@Type("streamlike")
public interface PsyStreamlike<T extends PsyObject>
	extends PsyObject
{
	default public PsyInteger psyCount()
	{
		long count=0;
		final var iterator=iterator();
		while(iterator.hasNext())
		{
			iterator.next();
			count++;
		}
		return PsyInteger.valueOf(count);
	}

	default public PsyStreamlike<T> psyConcat(final PsyStreamlike<T> oStreamlike)
	{
		return new PsyStreamlike<T>()
			{
				@Override
				public java.util.Iterator<T> iterator()
				{
					final var it1=iterator();
					final var it2=oStreamlike.iterator();
					return it1.hasNext()? it1: it2;
					/*
					return new java.util.Iterator<T>()
						{
							@Override
							public boolean hasNext()
							{
								return it1.hasNext() || it2.hasNext();
							}

							@Override
							public T next()
							{
								return it1.hasNext()? it1.hext(): it2.next();
							}
						};
					*/
				}
			};

	}

	default public PsyStreamlike<PsyObject> psyMap(final PsyProc oProc)
		throws PsyException
	{
		//final var interpreter=PsyContext.psyCurrentContext();
		//final var ostack=interpreter.operandStack();
		final var parentIterator=iterator();
		return new PsyStreamlike<PsyObject>()
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
								return (oProc.<T, PsyObject>asFunction()).apply(parentIterator.next());
							}
						};
				}

			};
	}

	/**
	*	Returns a Ψ-{@code streamlike} over elements of this object that
	*	satisfies the criterium calculated during Ψ-{@code proc} invocation.
	*
	*	@param oProc a procedure
	*	@return an iterable
	*/
	default public PsyStreamlike<T> psyFilter(final PsyProc oProc)
		throws PsyException
	{
		final var interpreter=PsyContext.psyCurrentContext();
		final var ostack=interpreter.operandStack();
		final var parentIterator=iterator();
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
								// TODO: move code to next()
								//try
								//{
									while(parentIterator.hasNext())
									{
										nextObject=parentIterator.next();
										//return (oProc.<T, PsyObject>asFunction(interpreter)).apply(parentIterator.next());
										/*ostack.push((PsyObject)nextObject);
										final int loopLevel=interpreter.pushLoopLevel();
										oProc.invoke(interpreter);
										interpreter.handleExecutionStack(loopLevel);
										ostack.popOperands(1);
										boolean check=ostack.<PsyBoolean>getBacked(0).booleanValue();*/
										boolean check=(oProc.<T>asPredicate()).test(nextObject);
										if(interpreter.getStopFlag())
											break;
										if(check)
											return true;
									}
								//}
								//catch(final PsyException e)
								//{
								//	this.e=e;
								//	return true;
								//}
								return false;
							}

							@Override
							public T next()
							{
								if(e!=null)
									throw new java.util.NoSuchElementException();
								return nextObject;
							}

							private PsyException e;

							private T nextObject;
						};
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
		final java.util.Iterator<T> iterator=iterator();
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

	default public T psyReduce(final T oIdentity, final PsyProc oProc)
	{
		T oIdent=oIdentity;
		final var iterator=iterator();
		final var op=oProc.<T>asBinaryOperator();
		while(iterator.hasNext())
			oIdent=op.apply(oIdent, iterator.next());
		return oIdent;
	}

	public java.util.Iterator<T> iterator();

}
