package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of {@code appendable}, a type of container that allow to
*	append objects (usually to the end, if it makes sense).
*
*	@param <T> a type of {@code object}s being appended.
*/
@Type("appendable")
public interface PsyAppendable<T extends PsyObject>
	extends PsyObject
{

	/**
	*	Appends a given {@code object} to this container.
	*
	*	@param o an {@code object} to append.
	*	@throws PsyException when an error occurs.
	*/
	public void psyAppend(final T o)
		throws PsyException;

	/**
	*	Appends all the {@code object}s from a given {@code iterable}
	*	enumeration to this container. When a given enumeration is the same as
	*	this container, first clone the enumeration, and then appends all the
	*	elements from the clone to avoid concurrent modification.
	*
	*	@param oEnumeration an {@code iterable} enumeration.
	*	@throws PsyException when an error occurs.
	*/
	default public void psyAppendAll(final PsyIterable<? extends T> oEnumeration)
		throws PsyException
	{
		for(T o: (this!=oEnumeration? oEnumeration: (PsyIterable<? extends T>)psyClone()))
			psyAppend(o);
	}

	// TODO
	public PsyAppendable psyReplicate(final PsyInteger oCount)
		throws PsyException;

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity20<PsyAppendable, PsyObject>
				("append", PsyAppendable::psyAppend),
			new PsyOperator.Arity20<PsyAppendable, PsyIterable>
				("appendall", PsyAppendable::psyAppendAll),
		};

}
