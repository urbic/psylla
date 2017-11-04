package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of Ψ-{@code iterable}, a type of an object that can be
*	iterated over.
*
*	@param <T> a type of elements returned by the iterator.
*/
@coneforest.psylla.Type("iterable")
public interface PsyIterable<T extends PsyObject>
	extends
		PsyObject,
		Iterable<T>
{

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
		final Interpreter interpreter=(Interpreter)PsyContext.psyCurrentContext();
		final OperandStack ostack=interpreter.operandStack();
		final java.util.Iterator<T> iterator=iterator();
		interpreter.pushLoopLevel();
		interpreter.executionStack().push(new PsyOperator("#forall_continue")
			{
				@Override
				public void action(Interpreter interpreter1)
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

	/**
	*	Returns a Ψ-{@code iterable} over elements of this object that
	*	satisfies the criterium calculated during Ψ-{@code proc} invocation.
	*
	*	@param oProc a procedure
	*	@return an iterable
	*/
	default public PsyIterable<T> psyGrep(final PsyProc oProc)
		throws PsyException
	{
		final Interpreter interpreter
			=(Interpreter)PsyContext.psyCurrentContext();
		final OperandStack ostack=interpreter.operandStack();
		final java.util.Iterator<T> parentIterator=iterator();
		return new PsyIterable<T>()
			{
				@Override
				public java.util.Iterator<T> iterator()
				{
					return new java.util.Iterator<T>()
						{
							@Override
							public boolean hasNext()
							{
								try
								{
									while(parentIterator.hasNext())
									{
										nextObject=parentIterator.next();
										ostack.push((PsyObject)nextObject);
										final int loopLevel=interpreter.pushLoopLevel();
										oProc.invoke(interpreter);
										interpreter.handleExecutionStack(loopLevel);
										ostack.popOperands(1);
										boolean check=ostack.<PsyBoolean>getBacked(0).booleanValue();
										if(interpreter.getStopFlag())
											break;
										if(check)
											return true;
									}
								}
								catch(final PsyException e)
								{
									this.e=e;
									return true;
								}
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

	default public PsyArray psyToArray()
		throws PsyException
	{
		final PsyArray oArray=new PsyArray();
		for(T o: this)
			oArray.psyAppend(o);
		return oArray;
	}

	default public PsyInteger psyCount()
	{
		long count=0L;
		for(T o: this)
			count++;
		return PsyInteger.valueOf(count);
	}

	default public PsyString psyUnite(final PsyStringy oSeparator)
		throws PsyException
	{
		final String separator=oSeparator.stringValue();
		final StringBuilder sb=new StringBuilder();
		final java.util.Iterator<T> iterator=iterator();
		try
		{
			while(iterator.hasNext())
			{
				sb.append(((PsyStringy)iterator.next()).stringValue());
				if(iterator.hasNext())
					sb.append(separator);
			}
		}
		catch(final ClassCastException e)
		{
			throw new PsyTypeCheckException();
		}
		return new PsyString(sb);
	}

}
