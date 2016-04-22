package coneforest.psi.core;
import coneforest.psi.*;

/**
 *	A representation of Ψ-{@code operator} object.
 */
public abstract class PsiOperator
	implements PsiAtomic
{
	public PsiOperator(final String name)
	{
		this.name=name;
	}

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
		final OperandStack ostack=interpreter.operandStack();
		ostack.clearBackup();
		try
		{
			action(interpreter);
		}
		catch(ClassCastException e)
		{
			ostack.restore();
			interpreter.handleError(new PsiTypeCheckException(this));
		}
		catch(PsiException e)
		{
			ostack.restore();
			e.setEmitter(this);
			interpreter.handleError(e);
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
		return name;
		//return name!=null? name: getClass().getSimpleName().substring(1);
	}

	public static class Arity01
		extends PsiOperator
	{
		@Override
		public void action(final Interpreter interpreter)
			throws ClassCastException, PsiException
		{
			interpreter.operandStack().push(handler.handle());
		}

		public Arity01(String name, Handler handler)
		{
			super(name);
			this.handler=handler;
		}

		private final Handler handler;

		@FunctionalInterface
		public static interface Handler
		{
			public PsiObject handle()
				throws PsiException;
		}
	}

	public static class Arity11
		extends PsiOperator
	{
		@Override
		public void action(final Interpreter interpreter)
			throws ClassCastException, PsiException
		{
			final OperandStack ostack=interpreter.operandStack();
			ostack.push(handler.handle(ostack.pop()));
		}

		public Arity11(String name, Handler handler)
		{
			super(name);
			this.handler=handler;
		}

		private final Handler handler;

		@FunctionalInterface
		public static interface Handler
		{
			public PsiObject handle(PsiObject o)
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
			final OperandStack ostack=interpreter.operandStack();
			final PsiObject[] ops=ostack.popOperands(2);
			ostack.push(handler.handle(ops[0], ops[1]));
		}

		public Arity21(String name, Handler handler)
		{
			super(name);
			this.handler=handler;
		}

		private final Handler handler;

		@FunctionalInterface
		public static interface Handler
		{
			public PsiObject handle(PsiObject o1, PsiObject o2)
				throws PsiException;
		}
	}

	public static class Arity10
		extends PsiOperator
	{
		@Override
		public void action(final Interpreter interpreter)
			throws ClassCastException, PsiException
		{
			final OperandStack ostack=interpreter.operandStack();
			handler.handle(ostack.pop());
		}

		public Arity10(String name, Handler handler)
		{
			super(name);
			this.handler=handler;
		}

		private final Handler handler;

		@FunctionalInterface
		public interface Handler
		{
			public void handle(PsiObject o)
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
			final OperandStack ostack=interpreter.operandStack();
			final PsiObject[] ops=ostack.popOperands(2);
			handler.handle(ops[0], ops[1]);
		}

		public Arity20(String name, Handler handler)
		{
			super(name);
			this.handler=handler;
		}

		private final Handler handler;

		@FunctionalInterface
		public static interface Handler
		{
			public void handle(PsiObject o1, PsiObject o2)
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
			final OperandStack ostack=interpreter.operandStack();
			final PsiObject[] ops=ostack.popOperands(3);
			handler.handle(ops[0], ops[1], ops[2]);
		}

		public Arity30(String name, Handler handler)
		{
			super(name);
			this.handler=handler;
		}

		private final Handler handler;

		public static interface Handler
		{
			public void handle(PsiObject o1, PsiObject o2, PsiObject o3)
				throws PsiException;
		}
	}

	public static class Arity31
		extends PsiOperator
	{
		@Override
		public void action(final Interpreter interpreter)
			throws ClassCastException, PsiException
		{
			final OperandStack ostack=interpreter.operandStack();
			final PsiObject[] ops=ostack.popOperands(3);
			ostack.push(handler.handle(ops[0], ops[1], ops[2]));
		}

		public Arity31(String name, Handler handler)
		{
			super(name);
			this.handler=handler;
		}

		private final Handler handler;

		public static interface Handler
		{
			public PsiObject handle(PsiObject o1, PsiObject o2, PsiObject o3)
				throws PsiException;
		}
	}

	public static class Action
		extends PsiOperator
	{
		@Override
		public void action(final Interpreter interpreter)
			throws ClassCastException, PsiException
		{
			handler.handle(interpreter);
		}

		public Action(String name, Handler handler)
		{
			super(name);
			this.handler=handler;
		}

		private final Handler handler;

		public static interface Handler
		{
			public void handle(Interpreter interpreter)
				throws PsiException;
		}
	}

	/*
	public static PsiOperator virtualVoidOperator(final String name, final String methodName,
		final int arity)
	{
		return new PsiOperator(name)
			{
				@Override
				public void action(Interpreter interpreter)
					throws PsiException
				{
					final OperandStack ostack=interpreter.operandStack();
					final PsiObject[] ops=ostack.popOperands(arity);
					final Class<?>[] ptypes=new Class<?>[arity-1];
					for(int i=1; i<arity; i++)
						//ptypes[i-1]=ops[i].getClass();
						ptypes[i-1]=PsiObject.class;
					final java.lang.invoke.MethodType methodType
						=java.lang.invoke.MethodType.methodType(void.class, ptypes);
					try
					{
						java.lang.invoke.MethodHandle methodHandle
							=lookup.findVirtual(ops[0].getClass(), methodName, methodType);
						System.out.println(methodHandle);
						for(int i=0; i<arity; i++)
							methodHandle=methodHandle.bindTo(ops[i]);
						System.out.println(methodHandle);
						methodHandle.invoke();
					}
					catch(NullPointerException e)
					{
						System.err.println("NULL: "+e);
						e.printStackTrace();
						throw new PsiTypeCheckException();
					}
					catch(NoSuchMethodException|IllegalAccessException e)
					{
						System.err.println("METHOD: "+e);
						e.printStackTrace();
						throw new PsiTypeCheckException();
					}
					catch(Throwable e)
					{
						System.err.println("THROWABLE: "+e);
						e.printStackTrace();
						throw (PsiException)e;
					}
				}

				@Override
				public String getName()
				{
					return name;
				}
			};
	}

	public static PsiOperator virtualReturningOperator(final String name, final String methodName,
		final int arity, final Class<? extends PsiObject> retclass)
	{
		return new PsiOperator(name)
			{
				@Override
				public void action(Interpreter interpreter)
					throws PsiException
				{
					final OperandStack ostack=interpreter.operandStack();
					final PsiObject[] ops=ostack.popOperands(arity);
					final Class<?>[] ptypes=new Class<?>[arity-1];
					for(int i=1; i<arity; i++)
						ptypes[i-1]=ops[i].getClass();
						//ptypes[i-1]=PsiObject.class;
					final java.lang.invoke.MethodType methodType
						//=java.lang.invoke.MethodType.methodType(PsiComplexNumeric.class, ptypes);
						=java.lang.invoke.MethodType.methodType(retclass, ptypes);
					try
					{
						java.lang.invoke.MethodHandle methodHandle
							=lookup.findVirtual(ops[0].getClass(), methodName, methodType);
						System.out.println(methodHandle);
						for(int i=0; i<arity; i++)
							methodHandle=methodHandle.bindTo(ops[i]);
						System.out.println(methodHandle);
						ostack.push((PsiObject)methodHandle.invoke());
					}
					catch(NullPointerException e)
					{
						System.err.println("NULL: "+e);
						e.printStackTrace();
						throw new PsiTypeCheckException();
					}
					catch(NoSuchMethodException|IllegalAccessException e)
					{
						System.err.println("METHOD: "+e);
						e.printStackTrace();
						throw new PsiTypeCheckException();
					}
					catch(Throwable e)
					{
						System.err.println("THROWABLE: "+e);
						e.printStackTrace();
						throw (PsiException)e;
					}
				}

				@Override
				public String getName()
				{
					return name;
				}
			};
	}
	*/

	private final String name;

	//private static java.lang.invoke.MethodHandles.Lookup lookup
	//	=java.lang.invoke.MethodHandles.publicLookup();
}
