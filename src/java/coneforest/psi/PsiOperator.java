package coneforest.psi;

/**
 *	A representation of Ψ-{@code operator} object.
 */
public abstract class PsiOperator
	implements PsiAtomic
{
	public PsiOperator()
	{
	}

	/*
	public PsiOperator(final String name)
	{
		this.name=name;
	}
	*/

	/**
	 *	@return a string {@code "operator"}.
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
		final OperandStack opstack=interpreter.operandStack();
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
	}

	abstract public void action(Interpreter interpreter)
		throws ClassCastException, PsiException;

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

	public static Object invokeVirtual11(String methodName, PsiObject[] ops)
		throws PsiException
	{
		try
		{
			return (PsiObject)java.lang.invoke.MethodHandles.publicLookup()
				.findVirtual(ops.getClass(), methodName,
					java.lang.invoke.MethodType.methodType(PsiObject.class))
				.invoke(ops);
		}
		catch(NoSuchMethodException|IllegalAccessException e)
		{
			System.out.println(e);
			throw new PsiTypeCheckException();
		}
		catch(Throwable e)
		{
			throw new PsiUnregisteredException();
		}
	}

	public static PsiObject invokeVirtual(String methodName, Class<? extends PsiObject> rtype, PsiObject[] ops, final int arity)
		throws PsiException
	{
			final Class<? extends PsiObject>[] ptypes=new Class[arity];
			for(int i=0; i<arity; i++)
			{
				ptypes[i]=ops[i].getClass();
				System.out.println(ptypes[i]);
			}
			ptypes[0]=PsiAdditive.class;
		try
		{
			return (PsiObject)java.lang.invoke.MethodHandles.publicLookup()
				//.findVirtual(ops[0].getClass(), methodName,
				.findVirtual(PsiAdditive.class, methodName,
					java.lang.invoke.MethodType.methodType(rtype, ptypes))
				.asSpreader(PsiObject[].class, arity)
				.invoke(ops);
		}
		catch(NoSuchMethodException|IllegalAccessException e)
		{
			System.out.println(e);
			throw new PsiTypeCheckException();
		}
		catch(Throwable e)
		{
			System.out.println(e);
			throw new PsiUnregisteredException();
		}
	}

	/*
	public static class Arity11<T extends PsiObject>
		extends PsiOperator
	{
		@Override
		public void action(final Interpreter interpreter)
			throws ClassCastException, PsiException
		{
			final OperandStack ostack=interpreter.operandStack();
			ostack.push(handler.handle(ostack.popOperands(1)));
		}

		public Arity11(String name, Handler handler)
		{
			super(name);
			this.handler=handler;
		}

		private final Handler handler;

		@FunctionalInterface
		public interface Handler
		{
			public PsiObject handle(PsiObject[] ops)
				throws PsiException;
		}
	}

	public static class Arity21
		extends PsiOperator
	{
		@Override
		public void action(final Interpreter interpreter)
			throws ClassCastException, PsiException
		{
			final OperandStack opstack=interpreter.operandStack();
			opstack.push(handler.handle(opstack.popOperands(2)));
		}

		public Arity21(String name, Handler handler)
		{
			super(name);
			this.handler=handler;
		}

		private final Handler handler;

		public interface Handler
		{
			public PsiObject handle(PsiObject[] ops)
				throws PsiException;
		}
	}

	public static class Arity20
		extends PsiOperator
	{
		@Override
		public void action(final Interpreter interpreter)
			throws ClassCastException, PsiException
		{
			final OperandStack opstack=interpreter.operandStack();
			handler.handle(opstack.popOperands(2));
		}

		public Arity20(String name, Handler handler)
		{
			super(name);
			this.handler=handler;
		}

		private final Handler handler;

		public interface Handler
		{
			public void handle(PsiObject[] ops)
				throws PsiException;
		}

	}

	public static class Arity30
		extends PsiOperator
	{
		@Override
		public void action(final Interpreter interpreter)
			throws ClassCastException, PsiException
		{
			final OperandStack opstack=interpreter.operandStack();
			handler.handle(opstack.popOperands(3));
		}

		public Arity30(String name, Handler handler)
		{
			super(name);
			this.handler=handler;
		}

		private final Handler handler;

		public interface Handler
		{
			public void handle(PsiObject[] ops)
				throws PsiException;
		}
	}

	protected String name;
	*/
}
