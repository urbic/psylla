package coneforest.psylla;

import coneforest.psylla.core.*;
import java.util.Optional;

/**
*	An action on the execution context.
*/
@FunctionalInterface
public interface ContextAction
{
	/**
	*	Performs this action on the specified execution context.
	*
	*	@param oContext the execution context.
	*	@throws PsyErrorException when an error occurs.
	*/
	public void perform(final PsyContext oContext)
		throws PsyErrorException;

	/**
	*	Returns the context action created from the consumer.
	*
	*	@param <T> the type of the input to the consumer.
	*	@param consumer the consumer.
	*	@return the context action created.
	*/
	public static <T extends PsyObject> ContextAction ofConsumer(final Consumer<T> consumer)
	{
		return oContext->
			{
				final var ostack=oContext.operandStackBacked(1);
				consumer.accept(ostack.getBacked(0));
			};
	}

	/**
	*	Returns the context action created from the supplier.
	*
	*	@param supplier the supplier.
	*	@return the context action created.
	*/
	public static ContextAction ofSupplier(final Supplier supplier)
	{
		return oContext->oContext.operandStack().push(supplier.get());
	}

	public static <T1 extends PsyObject, T2 extends PsyObject>
	ContextAction ofBiConsumer(final BiConsumer<T1, T2> biConsumer)
	{
		return oContext->
			{
				final var ostack=oContext.operandStackBacked(2);
				biConsumer.accept(ostack.getBacked(0), ostack.getBacked(1));
			};
	}

	public static <T1 extends PsyObject, T2 extends PsyObject, T3 extends PsyObject>
	ContextAction ofTriConsumer(final TriConsumer<T1, T2, T3> triConsumer)
	{
		return oContext->
			{
				final var ostack=oContext.operandStackBacked(3);
				triConsumer.accept(ostack.getBacked(0), ostack.getBacked(1), ostack.getBacked(2));
			};
	}

	public static <T extends PsyObject> ContextAction
	ofFunction(final Function<T> function)
	{
		return oContext->
			{
				final var ostack=oContext.operandStackBacked(1);
				ostack.push(function.apply(ostack.getBacked(0)));
			};
	}

	public static <T extends PsyObject> ContextAction
	ofOptionalFunction(final OptionalFunction<T> optionalFunction)
	{
		return oContext->
			{
				final var ostack=oContext.operandStackBacked(1);
				ostack.pushOptional(optionalFunction.apply(ostack.getBacked(0)));
			};
	}

	public static <T1 extends PsyObject, T2 extends PsyObject>
	ContextAction ofBiFunction(final BiFunction<T1, T2> biFunction)
	{
		return oContext->
			{
				final var ostack=oContext.operandStackBacked(2);
				ostack.push(biFunction.apply(ostack.getBacked(0), ostack.getBacked(1)));
			};
	}

	public static <T1 extends PsyObject, T2 extends PsyObject, T3 extends PsyObject>
	ContextAction ofTriFunction(final TriFunction<T1, T2, T3> triFunction)
	{
		return oContext->
			{
				final var ostack=oContext.operandStackBacked(3);
				ostack.push(triFunction.apply(ostack.getBacked(0), ostack.getBacked(1), ostack.getBacked(2)));
			};
	}

	@FunctionalInterface
	public static interface Consumer<T extends PsyObject>
	{
		public void accept(final T o)
			throws PsyErrorException;
	}

	@FunctionalInterface
	public static interface Supplier
	{
		public PsyObject get()
			throws PsyErrorException;
	}

	@FunctionalInterface
	public static interface BiConsumer<T1 extends PsyObject, T2 extends PsyObject>
	{
		public void accept(final T1 o1, final T2 o2)
			throws PsyErrorException;
	}

	@FunctionalInterface
	public static interface TriConsumer<T1 extends PsyObject, T2 extends PsyObject, T3 extends PsyObject>
	{
		public void accept(final T1 o1, final T2 o2, final T3 o3)
			throws PsyErrorException;
	}

	@FunctionalInterface
	public static interface Function<T extends PsyObject>
	{
		public PsyObject apply(final T o)
			throws PsyErrorException;
	}

	@FunctionalInterface
	public static interface OptionalFunction<T extends PsyObject>
	{
		public Optional<? extends PsyObject> apply(final T o)
			throws PsyErrorException;
	}

	@FunctionalInterface
	public static interface BiFunction<T1 extends PsyObject, T2 extends PsyObject>
	{
		public PsyObject apply(final T1 o1, final T2 o2)
			throws PsyErrorException;
	}

	@FunctionalInterface
	public static interface TriFunction<T1 extends PsyObject, T2 extends PsyObject, T3 extends PsyObject>
	{
		public PsyObject apply(final T1 o1, final T2 o2, final T3 o3)
			throws PsyErrorException;
	}
}
