package coneforest.psylla.core;

import coneforest.psylla.runtime.*;
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

	public default void invokeAndHandle(final PsyContext oContext)
	{
		final var execLevel=oContext.execLevel();
		invoke(oContext);
		oContext.handleExecutionStack(execLevel);
	}

	/**
	*	{@return a {@code Predicate} view of this {@code executable}}
	*
	*	@param <T> the type of the input to the predicate.
	*	@param oContext the {@code context} in which this predicate will be invoked.
	*/
	public default <T extends PsyObject> Predicate<T> asPredicate(final PsyContext oContext)
	{
		final var ostack=oContext.operandStack();
		return new Predicate<T>()
			{
				@Override
				public boolean test(final T o)
				{
					ostack.push(o);
					invokeAndHandle(oContext);
					return ((PsyBoolean)ostack.pop()).booleanValue();
					// TODO: stop
				}
			};
	}

	/**
	*	{@return a {@code Function} view of this {@code executable}}
	*
	*	@param <T> the type of the input to the function.
	*	@param <R> the type of the return value of the function.
	*	@param oContext the {@code context} in which this function will be invoked.
	*/
	@SuppressWarnings("unchecked")
	public default <T extends PsyObject, R extends PsyObject> Function<T, R> asFunction(final PsyContext oContext)
	{
		final var ostack=oContext.operandStack();
		return new Function<T, R>()
			{
				@Override
				public R apply(final T o)
				{
					ostack.push(o);
					invokeAndHandle(oContext);
					//throw new RuntimeException();
					return (R)ostack.pop();
					// TODO: stop
				}
			};
	}

	/**
	*	{@return an {@code UnaryOperator} view of this {@code executable}}
	*
	*	@param <T> the type of the input to the operator and its return value.
	*	@param oContext the {@code context} in which this operator will be invoked.
	*/
	@SuppressWarnings("unchecked")
	public default <T extends PsyObject> UnaryOperator<T> asUnaryOperator(final PsyContext oContext)
	{
		final var ostack=oContext.operandStack();
		return new UnaryOperator<T>()
			{
				@Override
				public T apply(final T o)
				{
					ostack.push(o);
					invokeAndHandle(oContext);
					return (T)ostack.pop();
					// TODO: stop
				}
			};
	}

	/**
	*	{@return an {@code BinaryOperator} view of this {@code executable}}
	*
	*	@param <T> the type of the inputs to the operator and its return value.
	*	@param oContext the {@code context} in which this operator will be invoked.
	*/
	public default <T extends PsyObject> BinaryOperator<T> asBinaryOperator(final PsyContext oContext)
	{
		final var ostack=oContext.operandStack();
		return new BinaryOperator<T>()
			{
				@SuppressWarnings("unchecked")
				@Override
				public T apply(final T o1, final T o2)
				{
					ostack.push(o1);
					ostack.push(o2);
					invokeAndHandle(oContext);
					return (T)ostack.pop();
					// TODO: stop
				}
			};
	}

	/**
	*	{@return an {@code Comparator} view of this {@code executable}}
	*
	*	@param <T> the type of the inputs to the comparator.
	*	@param oContext the {@code context} in which this comparator will be invoked.
	*/
	public default <T extends PsyObject> Comparator<T> asComparator(final PsyContext oContext)
	{
		final var ostack=oContext.operandStack();
		return new Comparator<T>()
			{
				@Override
				public int compare(final T o1, final T o2)
				{
					ostack.push(o1);
					ostack.push(o2);
					invokeAndHandle(oContext);
					return ((PsyInteger)ostack.pop()).intValue();
				}
			};
	}

	/**
	*	{@return an {@code Supplier} view of this {@code executable}}
	*
	*	@param <T> the type of the return value of the supplier.
	*	@param oContext the {@code context} in which this supplier will be invoked.
	*/
	public default <T extends PsyObject> Supplier<T> asSupplier(final PsyContext oContext)
	{
		final var ostack=oContext.operandStack();
		return new Supplier<T>()
			{
				@SuppressWarnings("unchecked")
				@Override
				public T get()
				{
					invokeAndHandle(oContext);
					return (T)ostack.pop();
				}
			};
	}

	/**
	*	{@return an {@code Consumer} view of this {@code executable}}
	*
	*	@param <T> the type of the input to the consumer.
	*	@param oContext the {@code context} in which this consumer will be invoked.
	*/
	public default <T extends PsyObject> Consumer<T> asConsumer(final PsyContext oContext)
	{
		final var ostack=oContext.operandStack();
		return new Consumer<T>()
			{
				@Override
				public void accept(final T o)
				{
					ostack.push(o);
					invokeAndHandle(oContext);
				}
			};
	}
}
