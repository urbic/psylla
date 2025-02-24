package coneforest.psylla.runtime;

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
	*	{@return the context action created from the consumer}
	*
	*	@param <T> the type of the input to the consumer.
	*	@param consumer the consumer.
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
	*	{@return the context action created from the supplier}
	*
	*	@param supplier the supplier.
	*/
	public static ContextAction ofSupplier(final Supplier supplier)
	{
		return oContext->oContext.operandStack().push(supplier.get());
	}

	/**
	*	{@return the context action created from the bi-consumer}
	*
	*	@param <T1> the type of the first input to the bi-consumer.
	*	@param <T2> the type of the second input to the bi-consumer.
	*	@param biConsumer the bi-consumer.
	*/
	public static <T1 extends PsyObject, T2 extends PsyObject>
	ContextAction ofBiConsumer(final BiConsumer<T1, T2> biConsumer)
	{
		return oContext->
			{
				final var ostack=oContext.operandStackBacked(2);
				biConsumer.accept(ostack.getBacked(0), ostack.getBacked(1));
			};
	}

	/**
	*	{@return the context action created from the tri-consumer}
	*
	*	@param <T1> the type of the first input to the tri-consumer.
	*	@param <T2> the type of the second input to the tri-consumer.
	*	@param <T3> the type of the third input to the tri-consumer.
	*	@param triConsumer the tri-consumer.
	*/
	public static <T1 extends PsyObject, T2 extends PsyObject, T3 extends PsyObject>
	ContextAction ofTriConsumer(final TriConsumer<T1, T2, T3> triConsumer)
	{
		return oContext->
			{
				final var ostack=oContext.operandStackBacked(3);
				triConsumer.accept(ostack.getBacked(0), ostack.getBacked(1), ostack.getBacked(2));
			};
	}

	/**
	*	{@return the context action created from the function}
	*
	*	@param <T> the type of the input to the function.
	*	@param function the function.
	*/
	public static <T extends PsyObject>
	ContextAction ofFunction(final Function<T> function)
	{
		return oContext->
			{
				final var ostack=oContext.operandStackBacked(1);
				ostack.push(function.apply(ostack.getBacked(0)));
			};
	}

	/**
	*	{@return the context action created from the function}
	*
	*	@param <T> the type of the input to the optional function.
	*	@param optionalFunction the optional function.
	*/
	public static <T extends PsyObject>
	ContextAction ofOptionalFunction(final OptionalFunction<T> optionalFunction)
	{
		return oContext->
			{
				final var ostack=oContext.operandStackBacked(1);
				ostack.pushOptional(optionalFunction.apply(ostack.getBacked(0)));
			};
	}

	/**
	*	{@return the context action created from the bi-function}
	*
	*	@param <T1> the type of the first input to the bi-function.
	*	@param <T2> the type of the second input to the bi-function.
	*	@param biFunction the bi-function.
	*/
	public static <T1 extends PsyObject, T2 extends PsyObject>
	ContextAction ofBiFunction(final BiFunction<T1, T2> biFunction)
	{
		return oContext->
			{
				final var ostack=oContext.operandStackBacked(2);
				ostack.push(biFunction.apply(ostack.getBacked(0), ostack.getBacked(1)));
			};
	}

	/**
	*	{@return the context action created from the tri-function}
	*
	*	@param <T1> the type of the first input to the tri-function.
	*	@param <T2> the type of the second input to the tri-function.
	*	@param <T3> the type of the third input to the tri-function.
	*	@param triFunction the tri-function.
	*/
	public static <T1 extends PsyObject, T2 extends PsyObject, T3 extends PsyObject>
	ContextAction ofTriFunction(final TriFunction<T1, T2, T3> triFunction)
	{
		return oContext->
			{
				final var ostack=oContext.operandStackBacked(3);
				ostack.push(triFunction.apply(ostack.getBacked(0), ostack.getBacked(1), ostack.getBacked(2)));
			};
	}

	/**
	*	Represents an operation that accepts a single input argument and returns no result.
	*
	*	@param <T> the type of the input to the operation.
	*/
	@FunctionalInterface
	public static interface Consumer<T extends PsyObject>
	{
		/**
		*	Performs this operation on the given argument.
		*
		*	@param o the input to the consumer.
		*	@throws PsyErrorException when an error occurs during operation.
		*/
		public void accept(final T o)
			throws PsyErrorException;
	}

	/**
	*	Represents a supplier of results.
	*/
	@FunctionalInterface
	public static interface Supplier
	{
		/**
		*	Gets a result.
		*
		*	@return a result.
		*	@throws PsyErrorException when an error occurs during operation.
		*/
		public PsyObject get()
			throws PsyErrorException;
	}

	/**
	*	Represents an operation that accepts two input arguments and returns no result.
	*
	*	@param <T1> the type of the first input to the operation.
	*	@param <T2> the type of the second input to the operation.
	*/
	@FunctionalInterface
	public static interface BiConsumer<T1 extends PsyObject, T2 extends PsyObject>
	{
		/**
		*	Performs this operation on the given arguments.
		*
		*	@param o1 the first input to the bi-consumer.
		*	@param o2 the second input to the bi-consumer.
		*	@throws PsyErrorException when an error occurs during operation.
		*/
		public void accept(final T1 o1, final T2 o2)
			throws PsyErrorException;
	}

	/**
	*	Represents an operation that accepts three input arguments and returns no result.
	*
	*	@param <T1> the type of the first input to the operation.
	*	@param <T2> the type of the second input to the operation.
	*	@param <T3> the type of the second input to the operation.
	*/
	@FunctionalInterface
	public static interface TriConsumer<T1 extends PsyObject, T2 extends PsyObject, T3 extends PsyObject>
	{
		/**
		*	Performs this operation on the given arguments.
		*
		*	@param o1 the first input to the tri-consumer.
		*	@param o2 the second input to the tri-consumer.
		*	@param o3 the third input to the tri-consumer.
		*	@throws PsyErrorException when an error occurs during operation.
		*/
		public void accept(final T1 o1, final T2 o2, final T3 o3)
			throws PsyErrorException;
	}

	/**
	*	Represents a function that accepts one argument and produces a result.
	*
	*	@param <T> the type of the input to the function.
	*/
	@FunctionalInterface
	public static interface Function<T extends PsyObject>
	{
		/**
		*	Applies this function to the given argument.
		*
		*	@param o the function argument.
		*	@return the function result.
		*	@throws PsyErrorException when an error occurs during operation.
		*/
		public PsyObject apply(final T o)
			throws PsyErrorException;
	}

	/**
	*	Represents a function that accepts one argument and produces an optional result.
	*
	*	@param <T> the type of the input to the function.
	*/
	@FunctionalInterface
	public static interface OptionalFunction<T extends PsyObject>
	{
		/**
		*	Applies this function to the given argument.
		*
		*	@param o the function argument.
		*	@return the function result.
		*	@throws PsyErrorException when an error occurs during operation.
		*/
		public Optional<? extends PsyObject> apply(final T o)
			throws PsyErrorException;
	}

	/**
	*	Represents a function that accepts two arguments and produces a result.
	*
	*	@param <T1> the type of the first input to the function.
	*	@param <T2> the type of the second input to the function.
	*/
	@FunctionalInterface
	public static interface BiFunction<T1 extends PsyObject, T2 extends PsyObject>
	{
		/**
		*	Applies this function to the given arguments.
		*
		*	@param o1 the first function argument.
		*	@param o2 the second function argument.
		*	@return the function result.
		*	@throws PsyErrorException when an error occurs during operation.
		*/
		public PsyObject apply(final T1 o1, final T2 o2)
			throws PsyErrorException;
	}

	/**
	*	Represents a function that accepts three arguments and produces a result.
	*
	*	@param <T1> the type of the first input to the function.
	*	@param <T2> the type of the second input to the function.
	*	@param <T3> the type of the third input to the function.
	*/
	@FunctionalInterface
	public static interface TriFunction<T1 extends PsyObject, T2 extends PsyObject, T3 extends PsyObject>
	{
		/**
		*	Applies this function to the given arguments.
		*
		*	@param o1 the first function argument.
		*	@param o2 the second function argument.
		*	@param o3 the third function argument.
		*	@return the function result.
		*	@throws PsyErrorException when an error occurs during operation.
		*/
		public PsyObject apply(final T1 o1, final T2 o2, final T3 o3)
			throws PsyErrorException;
	}
}
