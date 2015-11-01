package coneforest.psi;

/**
 *	A representation of Ψ-<code class="type">operator</code> object.
 */
public abstract class PsiOperator
	implements PsiAtomic
{
	/**
	 *	@return a string <code class="constant">"operator"</code>.
	 */
	@Override
	public String getTypeName()
	{
		return "operator";
	}

	/**
	 *	Execute this object in the context of the interpreter.  Calls {@link
	 *	#invoke(Interpreter)} method.
	 */
	public void execute(Interpreter interpreter)
	{
		invoke(interpreter);
	}

	/**
	 *	Invoke this object in the context of the interpreter performing
	 *	an action associated with it.
	 */
	public void invoke(Interpreter interpreter)
	{
		final OperandStack opstack=interpreter.getOperandStack();
		opstack.clearBackup();
		try
		{
			action(interpreter);
		}
		catch(ClassCastException e)
		{
			opstack.restore();
			interpreter.handleError("typecheck", this);
		}
		catch(PsiException e)
		{
			opstack.restore();
			interpreter.handleError(e.getName(), this);
		}
		catch(Throwable e)
		{
			opstack.restore();
			interpreter.handleError("unregistered", this);
		}
	}

	abstract public void action(Interpreter interpreter)
		throws ClassCastException, PsiException, Throwable;

	/**
	 *	Returns a syntatctic string representation of this operator.
	 *	A syntatctic representation has a form of
	 *	<code>"--<i>name</i>--"</code>, where <code><i>name</i></code> is a
	 *	string returned by {@link #getName()} method.
	 *
	 *	@return a syntatctic string representation of a name of this operator.
	 */
	@Override
	public String toSyntaxString()
	{
		return "--"+getName()+"--";
	}

	/**
	 *	Returns a name of this operator. A name returned is an operator’s
	 *	simple class name with first character (underscore) discarded. This
	 *	method must be overriden when using another naming scheme.
	 *
	 *	@return a name.
	 */
	public String getName()
	{
		return getClass().getSimpleName().substring(1);
	}

	protected static java.lang.invoke.MethodHandle
			getVirtualHandle(final Class<?> ftype, final String name, final Class<?> rtype, final Class<? extends PsiObject>... ptypes)
	{
		try
		{
			///*
			return java.lang.invoke.MethodHandles
					.lookup()
					.findVirtual(ftype, name,
						java.lang.invoke.MethodType.methodType(rtype, ptypes));
					//.asSpreader(PsiObject[].class, ptypes.length);
			//*/
			//return java.lang.invoke.MethodHandles
			//	.invoker(java.lang.invoke.MethodType.methodType(rtype, ptypes));
		}
		catch(NoSuchMethodException|IllegalAccessException e)
		{
			throw new ExceptionInInitializerError(e);
		}
	}

	protected static PsiObject invokeHandle(final java.lang.invoke.MethodHandle handle, final PsiObject[] params)
		throws PsiException
	{
		try
		{
			//return (PsiObject)handle.invoke(params);
			return (PsiObject)handle.invoke(params);
		}
		catch(java.lang.invoke.WrongMethodTypeException e)
		{
			System.out.println(e);
			throw new PsiUnregisteredException();
		}
		/*catch(ClassCastException e)
		{
			throw new PsiTypeCheckException();
		}*/
		catch(PsiException e)
		{
			throw e;
		}
		catch(Throwable e)
		{
			throw new PsiUnregisteredException();
		}
	}
}
