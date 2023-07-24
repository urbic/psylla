package coneforest.psylla.core;

import coneforest.psylla.*;

/**
*	A representation of a {@code formalset}, an abstraction of a finite set of
*	{@code object}s. This interface declares methods for appending, removal and
*	set operations.
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
	*	Removes an {@code object} from this set. If a given object is not
	*	present in this set, error does not occur.
	*
	*	@param o an {@code object}.
	*/
	public void psyRemove(final T o);

	/**
	*	Removes all the {@code object}s in a given {@code iterable} enumeration
	*	from this set. If some object is not present in this set, error does
	*	not occur.
	*
	*	@param oEnumeration an {@code iterable} enumeration.
	*/
	default public void psyRemoveAll(final PsyIterable<? extends T> oEnumeration)
	{
		if(this==oEnumeration)
			psyClear();
		else
			for(final T o: oEnumeration)
				psyRemove(o);
	}

	default public void psyRetainAll(final PsyIterable<? extends T> oEnumeration)
		throws PsyErrorException
	{
	//	for(final T obj: this)
	//		for(final T otherObj: iterable)
	//			if(!psyContains(obj).getValue())
	//				psyRemove(obj);
		System.out.println("NOP RETAINALL ITERABLE");
		// TODO
	}

	/**
	*	Returns a {@code boolean} object indicating whether a given {@code object} belongs to this
	*	set.
	*
	*	@param o an {@code object}.
	*	@return {@code boolean} indicating whether an object belongs to this set.
	*/
	public PsyBoolean psyContains(final T o);

	/**
	*	Removes all the elements from this set.
	*/
	@Override
	default public void psyClear()
	{
		for(final T o: this)
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
	default public void psyAppendAll(final PsyIterable<? extends T> oEnumeration)
		throws PsyLimitCheckException, PsyRangeCheckException
	{
		if(this==oEnumeration)
			return;
		for(final T o: oEnumeration)
			psyAppend(o);
	}

	/**
	*	Returns a {@code boolean} object indicating whether a given {@code formalset} set intersects
	*	with this set.
	*
	*	@param oSet a {@code formalset} set.
	*	@return {@code boolean} indicating whether a given {@code formalset} intersects with this
	*	set.
	*/
	default public PsyBoolean psyIntersects(final PsyFormalSet<? extends T> oSet)
	{
		for(final T o: oSet)
			if(psyContains(o).booleanValue())
				return PsyBoolean.TRUE;
		return PsyBoolean.FALSE;
	}

	@Override
	default public PsyFormalSet<T> psyReplicate(final PsyInteger oCount)
		throws PsyLimitCheckException, PsyRangeCheckException, PsyUnsupportedException
	{
		final long count=oCount.longValue();
		if(count<0)
			throw new PsyRangeCheckException();
		if(count>Integer.MAX_VALUE)
			throw new PsyLimitCheckException();
		if(count==0)
			return (PsyFormalSet<T>)psyNewEmpty();
		return (PsyFormalSet<T>)psyClone();
	}

	@Override
	default public String toSyntaxString()
	{
		return "("+toSyntaxStringHelper(this)+")";
	}

	default public String toSyntaxStringHelper(final PsyLengthy oLengthy)
	{
		final var sb=new StringBuilder();
		if(length()>0)
		{
			for(final PsyObject o: this)
			{
				if(o instanceof PsyLengthy)
					sb.append(o==oLengthy? "-"+o.typeName()+"-": ((PsyLengthy)o).toSyntaxString());
				else
					sb.append(o.toSyntaxString());
				sb.append(' ');
			}
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
	}

	/**
	*	Context action of the {@code contains} operator.
	*/
	@OperatorType("contains")
	public static final ContextAction PSY_CONTAINS
		=ContextAction.<PsyFormalSet, PsyObject>ofBiFunction(PsyFormalSet::psyContains);

	/**
	*	Context action of the {@code intersects} operator.
	*/
	@OperatorType("intersects")
	public static final ContextAction PSY_INTERSECTS
		=ContextAction.<PsyFormalSet, PsyFormalSet>ofBiFunction(PsyFormalSet::psyIntersects);

	/**
	*	Context action of the {@code remove} operator.
	*/
	@OperatorType("remove")
	public static final ContextAction PSY_REMOVE
		=ContextAction.<PsyFormalSet, PsyObject>ofBiConsumer(PsyFormalSet::psyRemove);

	/**
	*	Context action of the {@code removeall} operator.
	*/
	@OperatorType("removeall")
	public static final ContextAction PSY_REMOVEALL
		=ContextAction.<PsyFormalSet, PsyIterable>ofBiConsumer(PsyFormalSet::psyRemoveAll);

	/**
	*	Context action of the {@code retainall} operator.
	*/
	@OperatorType("retainall")
	public static final ContextAction PSY_RETAINALL
		=ContextAction.<PsyFormalSet, PsyIterable>ofBiConsumer(PsyFormalSet::psyRetainAll);
}
