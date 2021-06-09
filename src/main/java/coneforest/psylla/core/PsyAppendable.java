package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of Ψ-{@code appendable}, a type of container that allow to
*	append Ψ objects (usually to the end, if it makes sense).
*
*	@param <T> a type of Ψ-{@code object}s being appended.
*/
@Type("appendable")
public interface PsyAppendable<T extends PsyObject>
	extends PsyObject
{

	/**
	*	Appends a given Ψ-{@code object} to this container.
	*
	*	@param o a given Ψ-{@code object} to append.
	*	@throws PsyException when an error occurs.
	*/
	public void psyAppend(final T o)
		throws PsyException;

	/**
	*	Appends all the Ψ-{@code object}s from a given Ψ-{@code iterable}
	*	enumeration to this container. When a given enumeration is the same as
	*	this container, first clone the enumeration, and then appends all the
	*	elements from the clone to avoid concurrent modification.
	*
	*	@param oEnumeration a Ψ-{@code iterable} enumeration.
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
