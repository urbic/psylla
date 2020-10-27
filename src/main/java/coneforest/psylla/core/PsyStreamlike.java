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
			count++;
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
		/*
		final Interpreter interpreter=(Interpreter)PsyContext.psyCurrentContext();
		final OperandStack ostack=interpreter.operandStack();
		final int loopLevel=interpreter.pushLoopLevel();
		try
		{
			for(T o: this)
			{
				ostack.push(o);
				oProc.invoke(interpreter);
				interpreter.handleExecutionStack(loopLevel);
				if(interpreter.getStopFlag() || interpreter.getExitFlag())
					break;
			}
		}
		catch(final java.util.ConcurrentModificationException e)
		{
			throw new PsyConcurrentModificationException();
		}
		interpreter.popLoopLevel();
		interpreter.setExitFlag(false);
		*/
		final var interpreter=(Interpreter)PsyContext.psyCurrentContext();
		final var ostack=interpreter.operandStack();
		final java.util.Iterator<T> iterator=iterator();
		interpreter.pushLoopLevel();
		interpreter.executionStack().push(new PsyOperator("#forall_continue")
			{
				@Override
				public void action(final Interpreter interpreter1)
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
						interpreter1.executionStack().push(this);
						oProc.invoke(interpreter1);
					}
					else
						interpreter1.popLoopLevel();
				}
			});
	}

	public java.util.Iterator<T> iterator();
}
