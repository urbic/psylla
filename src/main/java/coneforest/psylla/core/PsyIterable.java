package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of Ψ-{@code iterable}, a type of an object that can be
*	iterated over.
*
*	@param <T> a type of elements returned by the iterator.
*/
@Type("iterable")
public interface PsyIterable<T extends PsyObject>
	extends
		PsyStreamlike<T>,
		Iterable<T>
{

	default public void psyForAll(final PsyObject oProc)
		throws PsyException
	{
		psyStream().psyForAll(oProc);
	}

	/**
	*	Returns a Ψ-{@code iterable} over elements of this object that
	*	satisfies the criterium calculated during Ψ-{@code proc} invocation.
	*
	*	@param oProc a procedure
	*	@return an iterable
	*/
	default public PsyIterable<T> psyFilter(final PsyProc oProc)
		throws PsyException
	{
		final var interpreter=(Interpreter)PsyContext.psyCurrentContext();
		final var ostack=interpreter.operandStack();
		final var parentIterator=iterator();
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
								// TODO: move code to naxt()
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
										boolean check=(oProc.<T>asPredicate(interpreter)).test(nextObject);
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

	default public PsyIterable<PsyObject> psyMap(final PsyProc oProc)
		throws PsyException
	{
		final var interpreter=(Interpreter)PsyContext.psyCurrentContext();
		final var ostack=interpreter.operandStack();
		final var parentIterator=iterator();
		return new PsyIterable<PsyObject>()
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
								return (oProc.<T, PsyObject>asFunction(interpreter)).apply(parentIterator.next());
							}
						};
				}

			};
	}

	default public PsyArray psyToArray()
		throws PsyException
	{
		final var oArray=new PsyArray();
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

	default public PsyStream psyStream()
	{
		return new PsyStream(java.util.stream.StreamSupport.<T>stream(spliterator(), false));
	}

	default public PsyString psyUnite(final PsyStringy oSeparator)
		throws PsyException
	{
		final var separator=oSeparator.stringValue();
		final var sb=new StringBuilder();
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
