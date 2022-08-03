package coneforest.psylla.core;
import coneforest.psylla.*;
import java.util.Comparator;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

@Type("executable")
public interface PsyExecutable
	extends PsyObject
{

	@Override
	public void invoke(final PsyContext oContext);

	default public void invokeAndWait(final PsyContext oContext)
	{
		final var execLevel=oContext.execLevel();
		invoke(oContext);
		oContext.handleExecutionStack(execLevel);
	}

	default public <T extends PsyObject> Predicate<T> asPredicate(final PsyContext oContext)
	{
		final var ostack=oContext.operandStack();
		return new Predicate<T>()
			{
				@Override
				public boolean test(final T o)
				{
					ostack.push(o);
					//try
					//{
					invokeAndWait(oContext);
					//}
					//catch(Throwable ex)
					//{
					//	throw new PsyUnregisteredException();
					//}
					return ((PsyBoolean)ostack.pop()).booleanValue();
					// TODO: stop
				}
			};
	}

	default public <T extends PsyObject, R extends PsyObject> Function<T, R> asFunction(final PsyContext oContext)
	{
		final var ostack=oContext.operandStack();
		return new Function<T, R>()
			{
				@Override
				public R apply(final T o)
				{
					ostack.push(o);
					invokeAndWait(oContext);
					return (R)ostack.pop();
					// TODO: stop
				}
			};
	}

	default public <T extends PsyObject> UnaryOperator<T> asUnaryOperator(final PsyContext oContext)
	{
		final var ostack=oContext.operandStack();
		return new UnaryOperator<T>()
			{
				@Override
				public T apply(final T o)
				{
					ostack.push(o);
					invokeAndWait(oContext);
					return (T)ostack.pop();
					// TODO: stop
				}
			};
	}

	default public <T extends PsyObject> BinaryOperator<T> asBinaryOperator(final PsyContext oContext)
	{
		final var ostack=oContext.operandStack();
		return new BinaryOperator<T>()
			{
				@Override
				public T apply(final T o1, final T o2)
				{
					ostack.push(o1);
					ostack.push(o2);
					invokeAndWait(oContext);
					return (T)ostack.pop();
					// TODO: stop
				}
			};
	}

	default public <T extends PsyObject> Comparator<T> asComparator(final PsyContext oContext)
	{
		final var ostack=oContext.operandStack();
		return new Comparator<T>()
			{
				@Override
				public int compare(final T o1, final T o2)
				{
					ostack.push(o1);
					ostack.push(o2);
					invokeAndWait(oContext);
					return ((PsyInteger)ostack.pop()).intValue();
				}
			};
	}

	default public <T extends PsyObject> Supplier<T> asSupplier(final PsyContext oContext)
	{
		final var ostack=oContext.operandStack();
		return new Supplier<T>()
			{
				@Override
				public T get()
				{
					invokeAndWait(oContext);
					return (T)ostack.pop();
				}
			};
	}

	default public <T extends PsyObject> Consumer<T> asConsumer(final PsyContext oContext)
	{
		final var ostack=oContext.operandStack();
		return new Consumer<T>()
			{
				@Override
				public void accept(T o)
				{
					ostack.push(o);
					invokeAndWait(oContext);
				}
			};
	}
}
