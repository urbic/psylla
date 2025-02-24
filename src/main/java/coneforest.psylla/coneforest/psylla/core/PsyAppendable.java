package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of {@code appendable}, a type of container that allow to append objects
*	(usually to the end, if it makes sense).
*
*	@param <T> a type of {@code object}s being appended.
*/
@Type("appendable")
public interface PsyAppendable<T extends PsyObject>
	extends PsyObject
{
	/**
	*	Context action of the {@code append} operator.
	*/
	@SuppressWarnings({"unchecked", "rawtypes"})
	@OperatorType("append")
	public static final ContextAction PSY_APPEND
		=ContextAction.<PsyAppendable, PsyObject>ofBiConsumer(PsyAppendable::psyAppend);

	/**
	*	Context action of the {@code appendall} operator.
	*/
	@SuppressWarnings({"unchecked", "rawtypes"})
	@OperatorType("appendall")
	public static final ContextAction PSY_APPENDALL
		=ContextAction.<PsyAppendable, PsyIterable>ofBiConsumer(PsyAppendable::psyAppendAll);

	/**
	*	Context action of the {@code replicate} operator.
	*/
	@SuppressWarnings({"rawtypes"})
	@OperatorType("replicate")
	public static final ContextAction PSY_REPLICATE
		=ContextAction.<PsyAppendable, PsyInteger>ofBiFunction(PsyAppendable::psyReplicate);

	/**
	*	Appends a given {@code object} to this container.
	*
	*	@param o an {@code object} to append.
	*	@throws PsyLimitCheckException when TODO.
	*	@throws PsyRangeCheckException when TODO.
	*/
	public void psyAppend(final T o)
		throws PsyLimitCheckException, PsyRangeCheckException;

	/**
	*	Appends all the {@code object}s from a given {@code iterable} enumeration to this container.
	*	When a given enumeration is the same as this container, first clone the enumeration, and
	*	then appends all the elements from the clone to avoid concurrent modification.
	*
	*	@param oIterable an {@code iterable} enumeration.
	*	@throws PsyLimitCheckException when TODO.
	*	@throws PsyRangeCheckException when TODO.
	*/
	@SuppressWarnings("unchecked")
	public default void psyAppendAll(final PsyIterable<? extends T> oIterable)
		throws PsyLimitCheckException, PsyRangeCheckException
	{
		// TODO
		for(final T o: this!=oIterable? oIterable: (PsyIterable<? extends T>)psyClone())
			psyAppend(o);
	}

	// TODO
	public PsyAppendable<T> psyReplicate(final PsyInteger oCount)
		throws PsyErrorException;
}
