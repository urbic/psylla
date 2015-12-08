package coneforest.psi;

/**
 *	A type of Ψ objects that can be iterated.
 *	@param <T> the type of elements returned by the iterator.
 */
public interface PsiIterable<T>
	extends
		PsiObject,
		Iterable<T>
{
	@Override
	default public String getTypeName()
	{
		return "iterable";
	}

	default public void psiForAll(final PsiObject proc)
		throws PsiException
	{
		final Interpreter interpreter=(Interpreter)PsiContext.psiCurrentContext();
		final OperandStack opstack=interpreter.getOperandStack();
		final int loopLevel=interpreter.pushLoopLevel();
		try
		{
			for(PsiObject element: (PsiIterable<? extends PsiObject>)this)
			{
				opstack.push(element);
				proc.invoke(interpreter);
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
	}

	/**
	 *	Returns a Ψ-iterable over elements of this object that satisfies the
	 *	criterium calculated during Ψ-proc invocation.
	 *	@param proc a procedure
	 *	@return an iterable
	 */
	default public PsiIterable<T> psiGrep(final PsiProcedure proc)
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
										OperandStack opstack=interpreter.getOperandStack();
										opstack.push((PsiObject)nextObject);
										final int loopLevel=interpreter.pushLoopLevel();
										proc.invoke(interpreter);
										interpreter.handleExecutionStack(loopLevel);
										opstack.ensureSize(1);
										boolean check=((PsiBoolean)opstack.pop()).booleanValue();
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

							private Interpreter interpreter
								=(Interpreter)PsiContext.psiCurrentContext();

						};
				}

				private PsiException exceptionOccured;

				private java.util.Iterator<T> parentIterator
					=PsiIterable.this.iterator();
			};
	}
}
