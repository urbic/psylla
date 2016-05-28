package coneforest.psi.core;

/**
*	A representation of a Ψ-{@code setlike}, an abstraction of a finite set of
*	Ψ-{@code object}s. This interface declares methods for appending, removal
*	and set operations.
*
*	@param <T> a type of the elements.
*/
public interface PsiSetlike<T extends PsiObject>
	extends
		PsiAppendable<T>,
		PsiContainer<T>
{
	/**
	*	@return a string {@code "setlike"}.
	*/
	@Override
	default public String getTypeName()
	{
		return "setlike";
	}

	/**
	*	Removes a Ψ-{@code object} from this set. If a given object is not
	*	present in this set, error does not occur.
	*
	*	@param o a given Ψ-{@code object}.
	*/
	public void psiRemove(final T o);

	/**
	*	Removes all the Ψ-{@code object}s in a given Ψ-{@code iterable}
	*	enumeration from this set. If some object is not present in this set,
	*	error does not occur.
	*
	*	@param oEnumeration a given Ψ-{@code iterable} enumeration.
	*/
	default public void psiRemoveAll(final PsiIterable<? extends T> oEnumeration)
	{
		if(this==oEnumeration)
			psiClear();
		else
			for(T o: oEnumeration)
				psiRemove(o);
	}

	default public void psiRetainAll(final PsiIterable<? extends T> oEnumeration)
		throws PsiException
	{
	//	for(T obj: this)
	//		for(T otherObj: iterable)
	//			if(!psiContains(obj).getValue())
	//				psiRemove(obj);
		System.out.println("NOP RETAINALL ITERABLE");
		// TODO
	}

	/**
	*	Returns a Ψ-{@code boolean} object indicating whether a given Ψ-{@code
	*	object} belongs to this set.
	*
	*	@param o a given Ψ-{@code object}.
	*	@return {@link PsiBoolean#TRUE}, if an object belongs to this set, and
	*	{@link PsiBoolean#FALSE} otherwise.
	*/
	public PsiBoolean psiContains(final T o);

	/**
	*	Removes all the elements from this set.
	*/
	@Override
	default public void psiClear()
	{
		for(T obj: this)
			psiRemove(obj);
	}

	/**
	*	Appends all the Ψ-{@code object}s from a given Ψ-{@code iterable}
	*	enumeration to this set. When a given enumeration is the same as this
	*	set, first clone the enumeration, and then appends all the elements
	*	from the clone.
	*
	*	@param oEnumeration a Ψ-{@code iterable} enumeration.
	*	@throws PsiException when an error occurs.
	*/
	@Override
	default public void psiAppendAll(final PsiIterable<? extends T> oEnumeration)
		throws PsiException
	{
		if(this==oEnumeration)
			return;
		for(T o: oEnumeration)
			psiAppend(o);
	}

	/**
	*	Returns a Ψ-{@code boolean} object indicating whether a given Ψ-{@code
	*	setlike} set intersects with this set.
	*
	*	@param oSet a given Ψ-{@code setlike} set.
	*	@return {@link PsiBoolean#TRUE}, if a given Ψ-{@code setlike} set
	*	intersects with this set, and {@link PsiBoolean#FALSE} otherwise.
	*/
	default public PsiBoolean psiIntersects(final PsiSetlike<? extends T> oSet)
	{
		for(T o: oSet)
			if(psiContains(o).booleanValue())
				return PsiBoolean.TRUE;
		return PsiBoolean.FALSE;
	}

	@Override
	default public PsiSetlike<T> psiReplicate(final PsiInteger oCount)
		throws PsiException
	{
		final long count=oCount.longValue();
		if(count<0)
			throw new PsiRangeCheckException();
		if(count>Integer.MAX_VALUE)
			throw new PsiLimitCheckException();
		if(count==0)
			return (PsiSetlike<T>)psiNewEmpty();
		return (PsiSetlike<T>)psiClone();
	}

	@Override
	default public String toSyntaxString()
	{
		return "("+toSyntaxStringHelper(this)+")";
	}

	default public String toSyntaxStringHelper(final PsiLengthy oLengthy)
	{
		final StringBuilder sb=new StringBuilder();
		if(length()>0)
		{
			for(PsiObject obj: this)
			{
				if(obj instanceof PsiLengthy)
					sb.append(obj==oLengthy? "-"+obj.getTypeName()+"-": ((PsiLengthy)obj).toSyntaxString());
				else
					sb.append(obj.toSyntaxString());
				sb.append(' ');
			}
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
	}
}
