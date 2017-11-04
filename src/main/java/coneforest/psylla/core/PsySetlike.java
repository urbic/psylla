package coneforest.psylla.core;

/**
*	A representation of a Ψ-{@code setlike}, an abstraction of a finite set of
*	Ψ-{@code object}s. This interface declares methods for appending, removal
*	and set operations.
*
*	@param <T> a type of the elements.
*/
@coneforest.psylla.Type("setlike")
public interface PsySetlike<T extends PsyObject>
	extends
		PsyAppendable<T>,
		PsyContainer<T>
{

	/**
	*	Removes a Ψ-{@code object} from this set. If a given object is not
	*	present in this set, error does not occur.
	*
	*	@param o a given Ψ-{@code object}.
	*/
	public void psyRemove(final T o);

	/**
	*	Removes all the Ψ-{@code object}s in a given Ψ-{@code iterable}
	*	enumeration from this set. If some object is not present in this set,
	*	error does not occur.
	*
	*	@param oEnumeration a given Ψ-{@code iterable} enumeration.
	*/
	default public void psyRemoveAll(final PsyIterable<? extends T> oEnumeration)
	{
		if(this==oEnumeration)
			psyClear();
		else
			for(T o: oEnumeration)
				psyRemove(o);
	}

	default public void psyRetainAll(final PsyIterable<? extends T> oEnumeration)
		throws PsyException
	{
	//	for(T obj: this)
	//		for(T otherObj: iterable)
	//			if(!psyContains(obj).getValue())
	//				psyRemove(obj);
		System.out.println("NOP RETAINALL ITERABLE");
		// TODO
	}

	/**
	*	Returns a Ψ-{@code boolean} object indicating whether a given Ψ-{@code
	*	object} belongs to this set.
	*
	*	@param o a given Ψ-{@code object}.
	*	@return {@link PsyBoolean#TRUE}, if an object belongs to this set, and
	*	{@link PsyBoolean#FALSE} otherwise.
	*/
	public PsyBoolean psyContains(final T o);

	/**
	*	Removes all the elements from this set.
	*/
	@Override
	default public void psyClear()
	{
		for(T obj: this)
			psyRemove(obj);
	}

	/**
	*	Appends all the Ψ-{@code object}s from a given Ψ-{@code iterable}
	*	enumeration to this set. When a given enumeration is the same as this
	*	set, first clone the enumeration, and then appends all the elements
	*	from the clone.
	*
	*	@param oEnumeration a Ψ-{@code iterable} enumeration.
	*	@throws PsyException when an error occurs.
	*/
	@Override
	default public void psyAppendAll(final PsyIterable<? extends T> oEnumeration)
		throws PsyException
	{
		if(this==oEnumeration)
			return;
		for(T o: oEnumeration)
			psyAppend(o);
	}

	/**
	*	Returns a Ψ-{@code boolean} object indicating whether a given Ψ-{@code
	*	setlike} set intersects with this set.
	*
	*	@param oSet a given Ψ-{@code setlike} set.
	*	@return {@link PsyBoolean#TRUE}, if a given Ψ-{@code setlike} set
	*	intersects with this set, and {@link PsyBoolean#FALSE} otherwise.
	*/
	default public PsyBoolean psyIntersects(final PsySetlike<? extends T> oSet)
	{
		for(T o: oSet)
			if(psyContains(o).booleanValue())
				return PsyBoolean.TRUE;
		return PsyBoolean.FALSE;
	}

	@Override
	default public PsySetlike<T> psyReplicate(final PsyInteger oCount)
		throws PsyException
	{
		final long count=oCount.longValue();
		if(count<0)
			throw new PsyRangeCheckException();
		if(count>Integer.MAX_VALUE)
			throw new PsyLimitCheckException();
		if(count==0)
			return (PsySetlike<T>)psyNewEmpty();
		return (PsySetlike<T>)psyClone();
	}

	@Override
	default public String toSyntaxString()
	{
		return "("+toSyntaxStringHelper(this)+")";
	}

	default public String toSyntaxStringHelper(final PsyLengthy oLengthy)
	{
		final StringBuilder sb=new StringBuilder();
		if(length()>0)
		{
			for(PsyObject obj: this)
			{
				if(obj instanceof PsyLengthy)
					sb.append(obj==oLengthy? "-"+obj.typeName()+"-": ((PsyLengthy)obj).toSyntaxString());
				else
					sb.append(obj.toSyntaxString());
				sb.append(' ');
			}
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
	}
}
