package coneforest.psylla.core;
import coneforest.psylla.*;

@Type("executable")
public interface PsyExecutable
	extends PsyObject
{

	@Override
	public void invoke(final PsyContext oContext);

	default public <T extends PsyObject> java.util.function.Predicate<T> asPredicate(final PsyContext oContext)
	{
		final var ostack=oContext.operandStack();
		return new java.util.function.Predicate<T>()
			{
				@Override
				public boolean test(final T o)
				{
					ostack.push(o);
					final var loopLevel=oContext.pushLoopLevel();
					invoke(oContext);
					oContext.handleExecutionStack(loopLevel);
					return ((PsyBoolean)ostack.pop()).booleanValue();
					// TODO: stop
				}
			};
	}

	default public <T extends PsyObject, R extends PsyObject> java.util.function.Function<T, R> asFunction(final PsyContext oContext)
	{
		final var ostack=oContext.operandStack();
		return new java.util.function.Function<T, R>()
			{
				@Override
				public R apply(final T o)
				{
					ostack.push(o);
					final var loopLevel=oContext.pushLoopLevel();
					invoke(oContext);
					oContext.handleExecutionStack(loopLevel);
					return (R)ostack.pop();
					// TODO: stop
				}
			};
	}

	default public <T extends PsyObject> java.util.function.UnaryOperator<T> asUnaryOperator(final PsyContext oContext)
	{
		final var ostack=oContext.operandStack();
		return new java.util.function.UnaryOperator<T>()
			{
				@Override
				public T apply(final T o)
				{
					ostack.push(o);
					//final var loopLevel=oContext.pushLoopLevel(); // TODO
					final var execLevel=oContext.execLevel(); // TODO
					invoke(oContext);
					//oContext.handleExecutionStack(loopLevel); // TODO
					oContext.handleExecutionStack(execLevel);	// TODO
					return (T)ostack.pop();
					// TODO: stop
				}
			};
	}

	default public <T extends PsyObject> java.util.function.BinaryOperator<T> asBinaryOperator(final PsyContext oContext)
	{
		final var ostack=oContext.operandStack();
		return new java.util.function.BinaryOperator<T>()
			{
				@Override
				public T apply(final T o1, final T o2)
				{
					ostack.push(o1);
					ostack.push(o2);
					//final var loopLevel=oContext.pushLoopLevel(); // TODO
					final var execLevel=oContext.execLevel(); // TODO
					invoke(oContext);
					//oContext.handleExecutionStack(loopLevel);	// TODO
					oContext.handleExecutionStack(execLevel);	// TODO
					return (T)ostack.pop();
					// TODO: stop
				}
			};
	}

	default public <T extends PsyObject> java.util.Comparator<T> asComparator(final PsyContext oContext)
	{
		final var ostack=oContext.operandStack();
		return new java.util.Comparator<T>()
			{
				@Override
				public int compare(final T o1, final T o2)
				{
					ostack.push(o1);
					ostack.push(o2);
					final var execLevel=oContext.execLevel();
					invoke(oContext);
					oContext.handleExecutionStack(execLevel);
					return ((PsyInteger)ostack.pop()).intValue();
				}
			};
	}

	default public <T extends PsyObject> java.util.function.Supplier<T> asSupplier(final PsyContext oContext)
	{
		final var ostack=oContext.operandStack();
		return new java.util.function.Supplier<T>()
			{
				@Override
				public T get()
				{
					final var execLevel=oContext.execLevel();
					invoke(oContext);
					oContext.handleExecutionStack(execLevel);
					return (T)ostack.pop();
				}
			};
	}
}
