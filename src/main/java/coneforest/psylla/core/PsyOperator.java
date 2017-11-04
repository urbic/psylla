package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of Ψ-{@code operator} object.
*/
@coneforest.psylla.Type("operator")
public abstract class PsyOperator
	implements PsyAtomic
{
	public PsyOperator(final String name)
	{
		this.name=name;
	}

	/**
	*	Execute this object in the context of the interpreter.  Calls {@link
	*	#invoke(Interpreter)} method.
	*/
	@Override
	public void execute(final Interpreter interpreter)
	{
		invoke(interpreter);
	}

	/**
	*	Invoke this object in the context of the interpreter performing
	*	an action associated with it.
	*/
	@Override
	public void invoke(final Interpreter interpreter)
	{
		final OperandStack ostack=interpreter.operandStack();
		ostack.clearBackup();
		try
		{
			action(interpreter);
		}
		catch(final ClassCastException e)
		{
			ostack.restore();
			interpreter.handleError(new PsyTypeCheckException(this));
		}
		catch(final PsyException e)
		{
			ostack.restore();
			e.setEmitter(this);
			interpreter.handleError(e);
		}
	}

	abstract public void action(final Interpreter interpreter)
		throws ClassCastException, PsyException;

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

	public static class Arity01
		extends PsyOperator
	{
		@Override
		public void action(final Interpreter interpreter)
			throws PsyException
		{
			interpreter.operandStack().push(handler.handle());
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
				throws PsyException;
		}
	}

	public static class Arity11<T extends PsyObject>
		extends PsyOperator
	{
		@Override
		public void action(final Interpreter interpreter)
			throws PsyException
		{
			final OperandStack ostack=interpreter.operandStackBacked(1);
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
				throws PsyException;
		}
	}

	public static class Arity21<T1 extends PsyObject, T2 extends PsyObject>
		extends PsyOperator
	{
		@Override
		public void action(final Interpreter interpreter)
			throws PsyException
		{
			final OperandStack ostack=interpreter.operandStackBacked(2);
			ostack.push(handler.handle(ostack.getBacked(0), ostack.getBacked(1)));
		}

		public Arity21(final String name, final Handler<T1, T2> handler)
		{
			super(name);
			this.handler=handler;
		}

		private final Handler<T1, T2> handler;

		@FunctionalInterface
		public static interface Handler<O1 extends PsyObject, O2 extends PsyObject>
		{
			public PsyObject handle(final O1 o1, final O2 o2)
				throws PsyException;
		}
	}

	public static class Arity10<T extends PsyObject>
		extends PsyOperator
	{
		@Override
		public void action(final Interpreter interpreter)
			throws PsyException
		{
			final OperandStack ostack=interpreter.operandStackBacked(1);
			handler.handle(ostack.getBacked(0));
		}

		public Arity10(final String name, final Handler<T> handler)
		{
			super(name);
			this.handler=handler;
		}

		private final Handler<T> handler;

		@FunctionalInterface
		public static interface Handler<O extends PsyObject>
		{
			public void handle(final O o)
				throws PsyException;
		}
	}

	public static class Arity20<T1 extends PsyObject, T2 extends PsyObject>
		extends PsyOperator
	{
		@Override
		public void action(final Interpreter interpreter)
			throws PsyException
		{
			final OperandStack ostack=interpreter.operandStackBacked(2);
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
				throws PsyException;
		}
	}

	public static class Arity30<T1 extends PsyObject, T2 extends PsyObject, T3 extends PsyObject>
		extends PsyOperator
	{
		@Override
		public void action(final Interpreter interpreter)
			throws PsyException
		{
			final OperandStack ostack=interpreter.operandStackBacked(3);
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
				throws PsyException;
		}
	}

	public static class Arity31<T1 extends PsyObject, T2 extends PsyObject, T3 extends PsyObject>
		extends PsyOperator
	{
		@Override
		public void action(final Interpreter interpreter)
			throws PsyException
		{
			final OperandStack ostack=interpreter.operandStackBacked(3);
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
				throws PsyException;
		}
	}

	public static class Action
		extends PsyOperator
	{
		@Override
		public void action(final Interpreter interpreter)
			throws ClassCastException, PsyException
		{
			handler.handle(interpreter);
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
			public void handle(final Interpreter interpreter)
				throws PsyException;
		}
	}

	/*
	@FunctionalInterface
	public static interface Handler21<T1 extends PsyObject, T2 extends PsyObject>
	{
		public PsyObject handle(final T1 o1, final T2 o2)
			throws PsyException;
	}

	public <T1 extends PsyObject, T2 extends PsyObject> PsyOperator(final String name, final Handler21<T1, T2> handler)
	{
		this(name);
	}
	*/

	private final String name;
}
