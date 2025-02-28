package coneforest.psylla.core;

import coneforest.psylla.runtime.*;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

/**
*	The representation of a {@code formalset}, an abstraction of a finite set of {@code object}s.
*	This interface declares methods for appending, removal and set operations.
*
*	@param <T> a type of the elements.
*/
@Type("formalset")
public interface PsyFormalSet<T extends PsyObject>
	extends
		PsyAppendable<T>,
		PsyContainer<T>
{

	/**
	*	Context action of the {@code contains} operator.
	*/
	@SuppressWarnings({"unchecked", "rawtypes"})
	@OperatorType("contains")
	public static final ContextAction PSY_CONTAINS
		=ContextAction.<PsyFormalSet, PsyObject>ofBiFunction(PsyFormalSet::psyContains);

	/**
	*	Context action of the {@code intersects} operator.
	*/
	@SuppressWarnings({"unchecked", "rawtypes"})
	@OperatorType("intersects")
	public static final ContextAction PSY_INTERSECTS
		=ContextAction.<PsyFormalSet, PsyFormalSet>ofBiFunction(PsyFormalSet::psyIntersects);

	/**
	*	Context action of the {@code remove} operator.
	*/
	@SuppressWarnings({"unchecked", "rawtypes"})
	@OperatorType("remove")
	public static final ContextAction PSY_REMOVE
		=ContextAction.<PsyFormalSet, PsyObject>ofBiConsumer(PsyFormalSet::psyRemove);

	/**
	*	Context action of the {@code removeall} operator.
	*/
	@SuppressWarnings({"unchecked", "rawtypes"})
	@OperatorType("removeall")
	public static final ContextAction PSY_REMOVEALL
		=ContextAction.<PsyFormalSet, PsyIterable>ofBiConsumer(PsyFormalSet::psyRemoveAll);

	/**
	*	Context action of the {@code retainall} operator.
	*/
	@SuppressWarnings({"unchecked", "rawtypes"})
	@OperatorType("retainall")
	public static final ContextAction PSY_RETAINALL
		=ContextAction.<PsyFormalSet, PsyIterable>ofBiConsumer(PsyFormalSet::psyRetainAll);

	/**
	*	Removes an {@code object} from this set. If a given object is not present in this set, error
	*	does not occur.
	*
	*	@param o an {@code object}.
	*/
	public void psyRemove(final T o);

	/**
	*	Removes all the {@code object}s in a given {@code iterable} enumeration from this set. If
	*	some object is not present in this set, error does not occur.
	*
	*	@param oEnumeration an {@code iterable} enumeration.
	*/
	public default void psyRemoveAll(final PsyIterable<? extends T> oEnumeration)
	{
		if(this==oEnumeration)
			psyClear();
		else
			for(final var o: oEnumeration)
				psyRemove(o);
	}

	public default void psyRetainAll(final PsyIterable<? extends T> oEnumeration)
		throws PsyErrorException
	{
	//	for(final var obj: this)
	//		for(final var otherObj: iterable)
	//			if(!psyContains(obj).getValue())
	//				psyRemove(obj);
		System.out.println("NOP RETAINALL ITERABLE");
		// TODO
	}

	/**
	*	{@return a {@code boolean} object indicating whether a given {@code object} belongs to this
	*	set}
	*
	*	@param o an {@code object}.
	*/
	public default PsyBoolean psyContains(final PsyObject o)
	{
		for(var o1: this)
			if(o1.equals(o))
				return PsyBoolean.TRUE;
		return PsyBoolean.FALSE;
	}

	/**
	*	Removes all the elements from this set.
	*/
	@Override
	public default void psyClear()
	{
		for(final var o: this)
			psyRemove(o);
	}

	/**
	*	Appends all the {@code object}s from a given {@code iterable} enumeration to this set. When
	*	a given enumeration is the same as this set, first clone the enumeration, and then appends
	*	all the elements from the clone.
	*
	*	@param oEnumeration an {@code iterable} enumeration.
	*	@throws PsyLimitCheckException when TODO.
	*	@throws PsyRangeCheckException when TODO.
	*/
	@Override
	public default void psyAppendAll(final PsyIterable<? extends T> oEnumeration)
		throws PsyLimitCheckException, PsyRangeCheckException
	{
		if(this==oEnumeration)
			return;
		for(final var o: oEnumeration)
			psyAppend(o);
	}

	/**
	*	{@return a {@code boolean} object indicating whether a given {@code formalset} set
	*	intersects with this set}
	*
	*	@param oSet a {@code formalset} set.
	*/
	public default PsyBoolean psyIntersects(final PsyFormalSet<? extends T> oSet)
	{
		for(final var o: oSet)
			if(psyContains(o).booleanValue())
				return PsyBoolean.TRUE;
		return PsyBoolean.FALSE;
	}

	@Override
	public default PsyFormalSet<T> psyReplicate(final PsyInteger oCount)
		throws PsyLimitCheckException, PsyRangeCheckException, PsyUnsupportedException
	{
		final long count=oCount.longValue();
		if(count<0)
			throw new PsyRangeCheckException();
		if(count>Integer.MAX_VALUE)
			throw new PsyLimitCheckException();
		if(count==0)
			return (PsyFormalSet<T>)psyNewEmpty();
		return psyClone();
	}

	@Override
	public PsyFormalSet<T> psyClone();

	@Override
	public default String toSyntaxString()
	{
		return toSyntaxStringHelper(new HashSet<PsyContainer<? extends PsyObject>>());
	}

	@Override
	public default String toSyntaxStringHelper(final Set<PsyContainer<? extends PsyObject>> processed)
	{
		if(!processed.add(this))
			return '%'+typeName()+'%';
		final var sj=new StringJoiner(" ", "(", ")");
		for(final var o: this)
			sj.add(o instanceof PsyContainer<? extends PsyObject> oContainer?
				oContainer.toSyntaxStringHelper(processed):
				o.toSyntaxString());
		return sj.toString();
	}

	@Override
	public boolean equals(final Object obj);
}
