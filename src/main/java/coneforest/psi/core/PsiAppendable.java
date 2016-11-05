package coneforest.psi.core;

/**
*	A representation of Ψ-{@code appendable}, a type of container that allow to
*	append Ψ objects (usually to the end, if it makes sense).
*
*	@param <T> a type of Ψ-{@code object}s being appended.
*/
public interface PsiAppendable<T extends PsiObject>
	extends PsiObject
{
	/**
	*	@return a string {@code "appendable"}.
	*/
	@Override
	default public String typeName()
	{
		return "appendable";
	}

	/**
	*	Appends a given Ψ-{@code object} to this container.
	*
	*	@param o a given Ψ-{@code object} to append.
	*	@throws PsiException when an error occurs.
	*/
	public void psiAppend(final T o)
		throws PsiLimitCheckException, PsiRangeCheckException;

	/**
	*	Appends all the Ψ-{@code object}s from a given Ψ-{@code iterable}
	*	enumeration to this container. When a given enumeration is the same as
	*	this container, first clone the enumeration, and then appends all the
	*	elements from the clone to avoid concurrent modification.
	*
	*	@param oEnumeration a Ψ-{@code iterable} enumeration.
	*	@throws PsiException when an error occurs.
	*/
	default public void psiAppendAll(final PsiIterable<? extends T> oEnumeration)
		throws PsiException
	{
		for(T o: (this!=oEnumeration? oEnumeration: (PsiIterable<? extends T>)psiClone()))
			psiAppend(o);
	}

	public PsiAppendable psiReplicate(final PsiInteger oCount)
		throws PsiException;
}
