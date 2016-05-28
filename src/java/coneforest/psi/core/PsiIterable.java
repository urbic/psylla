package coneforest.psi.core;
import coneforest.psi.*;

/**
*	A representation of Ψ-{@code iterable}, a type of an object that can be
*	iterated over.
*
*	@param <T> a type of elements returned by the iterator.
*/
public interface PsiIterable<T extends PsiObject>
	extends
		PsiObject,
		Iterable<T>
{
	/**
	 *	@return a string {@code "iterable"}.
	 */
	@Override
	default public String getTypeName()
	{
		return "iterable";
	}

	default public void psiForAll(final PsiObject oProc)
		throws PsiException
	{
		/*
		final Interpreter interpreter=(Interpreter)PsiContext.psiCurrentContext();
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
		catch(java.util.ConcurrentModificationException e)
		{
			throw new PsiConcurrentModificationException();
		}
		interpreter.popLoopLevel();
		interpreter.setExitFlag(false);
		*/
		final Interpreter interpreter=(Interpreter)PsiContext.psiCurrentContext();
		final OperandStack ostack=interpreter.operandStack();
		final java.util.Iterator<T> iterator=iterator();
		interpreter.pushLoopLevel();
		interpreter.executionStack().push(new PsiOperator("#forall_continue")
			{
				@Override
				public void action(Interpreter interpreter1)
					throws PsiException
				{
					if(interpreter1.getStopFlag()
							|| interpreter1.getExitFlag()
							|| !iterator.hasNext())
					{
						interpreter1.setExitFlag(false);
						interpreter1.popLoopLevel();
					}
					else
					{
						ostack.push(iterator.next());
						interpreter1.executionStack().push(this);
						oProc.invoke(interpreter1);
					}
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
	default public PsiIterable<T> psiGrep(final PsiProc oProc)
	{
		return new PsiIterable<T>()
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
										OperandStack ostack=interpreter.operandStack();
										ostack.push((PsiObject)nextObject);
										final int loopLevel=interpreter.pushLoopLevel();
										oProc.invoke(interpreter);
										interpreter.handleExecutionStack(loopLevel);
										ostack.ensureSize(1);
										boolean check=((PsiBoolean)ostack.pop()).booleanValue(); // TODO: pop()
										if(interpreter.getStopFlag()
												|| interpreter.getExitFlag())
											break;
										if(check)
											return true;
									}
								}
								catch(PsiException e)
								{
									exceptionOccured=e;
								}
								return false;
							}

							@Override
							public T next()
							{
								return nextObject;
							}

							private T nextObject;

							private final Interpreter interpreter
								=(Interpreter)PsiContext.psiCurrentContext();

						};
				}

				private PsiException exceptionOccured;

				private java.util.Iterator<T> parentIterator
					=PsiIterable.this.iterator();
			};
	}

	default public PsiArray psiToArray()
		throws PsiException
	{
		final PsiArray oArray=new PsiArray();
		for(T o: this)
			oArray.psiAppend(o);
		return oArray;
	}

	default public PsiInteger psiCount()
	{
		long count=0L;
		for(T o: this)
			count++;
		return PsiInteger.valueOf(count);
	}

	default public PsiString psiUnite(final PsiStringy oSeparator)
		throws PsiException
	{
		final String separator=oSeparator.stringValue();
		final StringBuilder sb=new StringBuilder();
		final java.util.Iterator<T> iterator=iterator();
		try
		{
			while(iterator.hasNext())
			{
				sb.append(((PsiStringy)iterator.next()).stringValue());
				if(iterator.hasNext())
					sb.append(separator);
			}
		}
		catch(ClassCastException e)
		{
			throw new PsiTypeCheckException();
		}
		return new PsiString(sb);
	}

}
