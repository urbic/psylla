package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	An implementation of Ψ-{@code proc}, a procedure.
*/
@coneforest.psylla.Type("proc")
public class PsyProc
	extends PsyArray
{
	public PsyProc()
	{
		super();
	}

	public PsyProc(final java.util.ArrayList<PsyObject> array)
	{
		super(array);
	}

	@Override
	public void invoke(final Interpreter interpreter)
	{
		try
		{
			final var estack=interpreter.executionStack();
			for(int i=length()-1; i>=0; i--)
				estack.push(get(i));
		}
		catch(final PsyRangeCheckException e)
		{
			// NOP
		}
	}

	@Override
	public PsyProc psyClone()
	{
		return new PsyProc((java.util.ArrayList<PsyObject>)array.clone());
	}

	@Override
	public String toSyntaxString()
	{
		return "{"+toSyntaxStringHelper(this)+"}";
	}

	public PsyProc psyBind()
	{
		final var dstack=((Interpreter)PsyContext.psyCurrentContext()).dictStack();

		final var agenda=new java.util.ArrayList<PsyProc>();
		final var bound=new java.util.HashSet<PsyProc>();

		agenda.add(this);

		while(!agenda.isEmpty())
		{
			final var oProc=agenda.remove(0);
			if(!bound.add(oProc))
				break;
			for(int i=0; i<oProc.length(); i++)
			{
				try
				{
					final var o=oProc.get(i);
					if(o instanceof PsyProc)
						agenda.add((PsyProc)o);
					else if(o instanceof PsyCommand)
					{
						final var oNew=dstack.load((PsyCommand)o);
						if(oNew instanceof PsyOperator
								|| oNew instanceof PsyMark
								|| oNew instanceof PsyNull)
							oProc.put(i, oNew);
					}
				}
				catch(final PsyException e)
				{
					// NOP
				}
			}
		}

		return this;
	}

	public <T extends PsyObject> java.util.function.Predicate<T>
		asPredicate(final Interpreter interpreter)
	{
		final var ostack=interpreter.operandStack();
		return new java.util.function.Predicate<T>()
			{
				@Override
				public boolean test(final T o)
				{
					ostack.push(o);
					final var loopLevel=interpreter.pushLoopLevel();
					invoke(interpreter);
					interpreter.handleExecutionStack(loopLevel);
					return ((PsyBoolean)ostack.pop()).booleanValue();
					// TODO: stop
				}
			};
	}

	public <T extends PsyObject, R extends PsyObject> java.util.function.Function<T, R>
		asFunction(final Interpreter interpreter)
	{
		final var ostack=interpreter.operandStack();
		return new java.util.function.Function<T, R>()
			{
				@Override
				public R apply(final T o)
				{
					ostack.push(o);
					final var loopLevel=interpreter.pushLoopLevel();
					invoke(interpreter);
					interpreter.handleExecutionStack(loopLevel);
					return (R)ostack.pop();
					// TODO: stop
				}
			};
	}

	public <T extends PsyObject> java.util.function.UnaryOperator<T>
		asUnaryOperator(final Interpreter interpreter)
	{
		final var ostack=interpreter.operandStack();
		return new java.util.function.UnaryOperator<T>()
			{
				@Override
				public T apply(final T o)
				{
					ostack.push(o);
					final var loopLevel=interpreter.pushLoopLevel();
					invoke(interpreter);
					interpreter.handleExecutionStack(loopLevel);
					return (T)ostack.pop();
					// TODO: stop
				}
			};
	}

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity11<PsyProc>("bind", PsyProc::psyBind),
		};
}
