package coneforest.psylla.core.types;

import coneforest.psylla.Type;
import coneforest.psylla.Operator;
import coneforest.psylla.core.errors.PsyError;
import coneforest.psylla.core.errors.PsyTypeCheck;
import coneforest.psylla.core.errors.PsyUndefined;

/**
*	A representation of {@code operator}.
*/
@Type("operator")
public abstract class PsyOperator
	implements
		PsyAtomic,
		PsyExecutable
{
	protected PsyOperator(final String name)
	{
		this.name=name;
	}

	public PsyOperator(final String name, final Action action)
	{
		this(name);
	}

	/**
	*	Execute this object in the context of the interpreter. Calls {@link #invoke(PsyContext)}
	*	method.
	*
	*	@param oContext
	*/
	@Override
	public void execute(final PsyContext oContext)
	{
		invoke(oContext);
	}

	/**
	*	Invoke this object in the context of the interpreter performing an action associated with
	*	it.
	*
	*	@param oContext the context
	*/
	@Override
	public void invoke(final PsyContext oContext)
	{
		final var ostack=oContext.operandStack();
		ostack.clearBackup();
		try
		{
			perform(oContext);
		}
		catch(final ClassCastException ex)
		{
			ostack.rollback();
			var e=new PsyTypeCheck();
			e.setEmitter(this);
			final var estack=oContext.executionStack();
			final var dstack=oContext.dictStack();
			e.setStacks(ostack, estack, dstack);
			e.invoke(oContext);
		}
		catch(final PsyError e)
		{
			ostack.rollback();
			e.setEmitter(this);
			final var estack=oContext.executionStack();
			final var dstack=oContext.dictStack();
			e.setStacks(ostack, estack, dstack);
			e.invoke(oContext);
		}
	}

	abstract public void perform(final PsyContext oContext)
		throws ClassCastException, PsyError;

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
	}

	public String getPrefix()
	{
		final var prefixOffset=name.indexOf('@');
		return prefixOffset<0? "core": name.substring(0, prefixOffset);
	}

	public String getSimpleName()
	{
		return name.substring(getPrefix().length()+1);
	}

	private final String name;

	public static class Method
		extends PsyOperator
	{
		private final Action.Handler handler;

		public Method(final java.lang.reflect.Method method)
			throws PsyError
		{
			super(method.getAnnotation(Operator.class).value());
			try
			{
				final var mh=java.lang.invoke.MethodHandles.lookup().unreflect(method);
				final var mht=mh.type();
				if(mht.returnType().equals(void.class))
					this.handler=oContext->
						{
							final var ostack=oContext.operandStackBacked(mht.parameterCount());
							final var params=new PsyObject[mht.parameterCount()];
							for(int i=0; i<mht.parameterCount(); i++)
							{
								params[i]=ostack.getBacked(i);
							}
							try
							{
								mh.invokeWithArguments((Object[])params);
							}
							catch(final Throwable ex)
							{
								// TODO: throw new PsyError(e);
								System.out.println("THROWABLE "+ex);
							}
						};
				else
					this.handler=oContext->
						{
							final var ostack=oContext.operandStackBacked(mht.parameterCount());
							final var params=new PsyObject[mht.parameterCount()];
							for(int i=0; i<mht.parameterCount(); i++)
							{
								params[i]=ostack.getBacked(i);
								//System.out.println("GET BACKED");
							}
							try
							{
								//System.out.println("CALL OP "+oRet);
								ostack.push((PsyObject)mh.invokeWithArguments((Object[])params));
							}
							catch(final Throwable ex)
							{
								// TODO: throw new PsyError(ex);
								//System.out.println("THROWABLE "+ex);
							}
						};
				}
				catch(IllegalAccessException ex)
				{
					// TODO
					throw new PsyUndefined();
				}
		}

		@Override
		public void perform(final PsyContext oContext)
			throws PsyError
		{
			handler.handle(oContext);
		}
	}
		/*

		try
		{
			final var mh=java.lang.invoke.MethodHandles.lookup().unreflect(method);
			final var mht=mh.type();
			//System.out.println("RETURNS "+name+" "+mht.returnType());
			if(mht.returnType().equals(void.class))
				return new PsyOperator(name)
					{
						@Override
						public void perform(final Interpreter interpreter)
							throws PsyError
						{
							final var ostack=interpreter.operandStackBacked(mht.parameterCount());
							final var params=new PsyObject[mht.parameterCount()];
							for(int i=0; i<mht.parameterCount(); i++)
							{
								params[i]=ostack.getBacked(i);
							}
							try
							{
								mh.invokeWithArguments((Object[])params);
							}
							catch(final Throwable ex)
							{
								// TODO: throw new PsyError(ex);
								System.out.println("THROWABLE "+ex);
							}
						}
					};
			else
				return new PsyOperator(name)
					{
						@Override
						public void perform(final Interpreter interpreter)
							throws PsyError
						{
							final var ostack=interpreter.operandStackBacked(mht.parameterCount());
							final var params=new PsyObject[mht.parameterCount()];
							for(int i=0; i<mht.parameterCount(); i++)
							{
								params[i]=ostack.getBacked(i);
								//System.out.println("GET BACKED");
							}
							try
							{
								PsyObject oRet=(PsyObject)mh.invokeWithArguments((Object[])params);
								//System.out.println("CALL OP "+oRet);
								ostack.push(oRet);
							}
							catch(final Throwable ex)
							{
								// TODO: throw new PsyError(ex);
								//System.out.println("THROWABLE "+ex);
							}
						}
					};
		}
		catch(final IllegalAccessException ex)
		{
			// TODO
		}
		return null;
	}
	*/

	/*
	public static PsyOperator valueOf(final java.lang.reflect.Method method)
	{
		try
		{
			final String name=method.getAnnotation(Operator.class).value();
			final var mh=java.lang.invoke.MethodHandles.lookup().unreflect(method);
			final var mht=mh.type();
			//System.out.println("RETURNS "+name+" "+mht.returnType());
			if(mht.returnType().equals(void.class))
				return new PsyOperator(name)
					{
						@Override
						public void perform(final Interpreter interpreter)
							throws PsyError
						{
							final var ostack=interpreter.operandStackBacked(mht.parameterCount());
							final var params=new PsyObject[mht.parameterCount()];
							for(int i=0; i<mht.parameterCount(); i++)
							{
								params[i]=ostack.getBacked(i);
								//System.out.println("GET BACKED");
							}
							try
							{
								mh.invokeWithArguments((Object[])params);
							}
							catch(final Throwable ex)
							{
								// TODO: throw new PsyError(ex);
								//System.out.println("THROWABLE "+ex);
							}
						}
					};
			else
				return new PsyOperator(name)
					{
						@Override
						public void perform(final Interpreter interpreter)
							throws PsyError
						{
							final var ostack=interpreter.operandStackBacked(mht.parameterCount());
							final var params=new PsyObject[mht.parameterCount()];
							for(int i=0; i<mht.parameterCount(); i++)
							{
								params[i]=ostack.getBacked(i);
								//System.out.println("GET BACKED");
							}
							try
							{
								PsyObject oRet=(PsyObject)mh.invokeWithArguments((Object[])params);
								//System.out.println("CALL OP "+oRet);
								ostack.push(oRet);
							}
							catch(final Throwable ex)
							{
								// TODO: throw new PsyError(ex);
								//System.out.println("THROWABLE "+ex);
							}
						}
					};
		}
		catch(final IllegalAccessException ex)
		{
			// TODO
		}
		return null;
	}

	public static PsyOperator valueOf(final java.lang.reflect.Constructor constructor)
	{
		try
		{
			final String name=((Operator)constructor.getAnnotation(Operator.class)).value();
			final var mh=java.lang.invoke.MethodHandles.lookup().unreflectConstructor(constructor);
			final var mht=mh.type();
			//System.out.println("RETURNS "+name+" "+mht.returnType());
			return new PsyOperator(name)
				{
					@Override
					public void perform(final Interpreter interpreter)
						throws PsyError
					{
						final var ostack=interpreter.operandStackBacked(mht.parameterCount());
						final var params=new PsyObject[mht.parameterCount()];
						for(int i=0; i<mht.parameterCount(); i++)
						{
							params[i]=ostack.getBacked(i);
							//System.out.println("GET BACKED");
						}
						try
						{
							PsyObject oRet=(PsyObject)mh.invokeWithArguments((Object[])params);
							//System.out.println("CALL OP "+oRet);
							ostack.push(oRet);
						}
						catch(final Throwable ex)
						{
							// TODO: throw new PsyError(ex);
							//System.out.println("THROWABLE "+ex);
						}
					}
				};
		}
		catch(final IllegalAccessException ex)
		{
			// TODO
		}
		return null;
	}
	*/

	public static class Arity00
		extends PsyOperator
	{
		@Override
		public void perform(final PsyContext oContext)
			throws PsyError
		{
			handler.handle();
		}

		public Arity00(final String name, final Handler handler)
		{
			super(name);
			this.handler=handler;
		}

		private final Handler handler;

		@FunctionalInterface
		public static interface Handler
		{
			public void handle()
				throws PsyError;
		}
	}

	public static class Arity01
		extends PsyOperator
	{
		@Override
		public void perform(final PsyContext oContext)
			throws PsyError
		{
			oContext.operandStack().push(handler.handle());
		}

		public Arity01(final String name, final Handler handler)
		{
			super(name);
			this.handler=handler;
		}

		private final Handler handler;

		@FunctionalInterface
		public static interface Handler
		{
			public PsyObject handle()
				throws PsyError;
		}
	}

	public static class Arity11<T extends PsyObject>
		extends PsyOperator
	{
		@Override
		public void perform(final PsyContext oContext)
			throws PsyError
		{
			final var ostack=oContext.operandStackBacked(1);
			ostack.push(handler.handle(ostack.getBacked(0)));
		}

		public Arity11(final String name, final Handler<T> handler)
		{
			super(name);
			this.handler=handler;
		}

		private final Handler<T> handler;

		@FunctionalInterface
		public static interface Handler<O extends PsyObject>
		{
			public PsyObject handle(final O o)
				throws PsyError;
		}
	}

	public static class Arity21<T1 extends PsyObject, T2 extends PsyObject>
		extends PsyOperator
	{
		@Override
		public void perform(final PsyContext oContext)
			throws PsyError
		{
			final var ostack=oContext.operandStackBacked(2);
			ostack.push(handler.handle(ostack.getBacked(0), ostack.getBacked(1)));
		}

		// TODO extends
		public Arity21(final String name, final Handler<? extends T1, ? extends T2> handler)
		{
			super(name);
			this.handler=handler;
		}

		// TODO extends
		private final Handler<? extends T1, ? extends T2> handler;

		@FunctionalInterface
		public static interface Handler<O1 extends PsyObject, O2 extends PsyObject>
		{
			public PsyObject handle(final O1 o1, final O2 o2)
				throws PsyError;
		}
	}

	public static class Arity10<T extends PsyObject>
		extends PsyOperator
	{
		@Override
		public void perform(final PsyContext oContext)
			throws PsyError
		{
			final var ostack=oContext.operandStackBacked(1);
			handler.handle(ostack.getBacked(0));
		}

		public Arity10(final String name, final Handler<T> handler)
		{
			super(name);
			this.handler=handler;
		}

		protected final Handler<T> handler;

		@FunctionalInterface
		public static interface Handler<O extends PsyObject>
		{
			public void handle(final O o)
				throws PsyError;
		}
	}

	public static class Arity20<T1 extends PsyObject, T2 extends PsyObject>
		extends PsyOperator
	{
		@Override
		public void perform(final PsyContext oContext)
			throws PsyError
		{
			final var ostack=oContext.operandStackBacked(2);
			handler.handle(ostack.getBacked(0), ostack.getBacked(1));
		}

		public Arity20(final String name, final Handler<T1, T2> handler)
		{
			super(name);
			this.handler=handler;
		}

		private final Handler<T1, T2> handler;

		@FunctionalInterface
		public static interface Handler<O1 extends PsyObject, O2 extends PsyObject>
		{
			public void handle(final O1 o1, final O2 o2)
				throws PsyError;
		}
	}

	public static class Arity30<T1 extends PsyObject, T2 extends PsyObject, T3 extends PsyObject>
		extends PsyOperator
	{
		@Override
		public void perform(final PsyContext oContext)
			throws PsyError
		{
			final var ostack=oContext.operandStackBacked(3);
			handler.handle(ostack.getBacked(0), ostack.getBacked(1), ostack.getBacked(2));
		}

		public Arity30(final String name, final Handler<T1, T2, T3> handler)
		{
			super(name);
			this.handler=handler;
		}

		private final Handler<T1, T2, T3> handler;

		@FunctionalInterface
		public static interface Handler<O1 extends PsyObject, O2 extends PsyObject, O3 extends PsyObject>
		{
			public void handle(final O1 o1, final O2 o2, final O3 o3)
				throws PsyError;
		}
	}

	public static class Arity31<T1 extends PsyObject, T2 extends PsyObject, T3 extends PsyObject>
		extends PsyOperator
	{
		@Override
		public void perform(final PsyContext oContext)
			throws PsyError
		{
			final var ostack=oContext.operandStackBacked(3);
			ostack.push(handler.handle(ostack.getBacked(0), ostack.getBacked(1), ostack.getBacked(2)));
		}

		public Arity31(final String name, final Handler<T1, T2, T3> handler)
		{
			super(name);
			this.handler=handler;
		}

		private final Handler<T1, T2, T3> handler;

		@FunctionalInterface
		public static interface Handler<O1 extends PsyObject, O2 extends PsyObject, O3 extends PsyObject>
		{
			public PsyObject handle(final O1 o1, final O2 o2, final O3 o3)
				throws PsyError;
		}
	}

	public static class Action
		extends PsyOperator
	{
		@Override
		public void perform(final PsyContext oContext)
			throws ClassCastException, PsyError
		{
			handler.handle(oContext);
		}

		public Action(final String name, final Handler handler)
		{
			super(name);
			this.handler=handler;
		}

		private final Handler handler;

		@FunctionalInterface
		public static interface Handler
		{
			public void handle(final PsyContext oContext)
				throws PsyError;
		}
	}

	/*
	@FunctionalInterface
	public static interface Handler21<T1 extends PsyObject, T2 extends PsyObject>
	{
		public PsyObject handle(final T1 o1, final T2 o2)
			throws PsyError;
	}

	public <T1 extends PsyObject, T2 extends PsyObject> PsyOperator(final String name, final Handler21<T1, T2> handler)
	{
		this(name);
	}
	*/

}
