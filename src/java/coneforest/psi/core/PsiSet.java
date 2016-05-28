package coneforest.psi.core;

/**
 *	A representation of Ψ-{@code set} object.
 */
public class PsiSet
	implements PsiSetlike<PsiObject>
{
	public PsiSet()
	{
		this(new java.util.HashSet<PsiObject>());
	}

	public PsiSet(final java.util.HashSet<PsiObject> set)
	{
		this.set=set;
	}

	/**
	*	@return a string {@code "set"}.
	*/
	@Override
	public String getTypeName()
	{
		return "set";
	}

	@Override
	public PsiSet psiClone()
	{
		return new PsiSet((java.util.HashSet<PsiObject>)set.clone());
	}

	@Override
	public int length()
	{
		return set.size();
	}

	@Override
	public void psiAppend(final PsiObject o)
	{
		set.add(o);
	}

	@Override
	public void psiRemove(final PsiObject o)
	{
		set.remove(o);
	}

	@Override
	public PsiBoolean psiContains(final PsiObject o)
	{
		return PsiBoolean.valueOf(set.contains(o));
	}

	@Override
	public void psiClear()
	{
		set.clear();
	}

	@Override
	public java.util.Iterator<PsiObject> iterator()
	{
		return set.iterator();
	}

	private final java.util.HashSet<PsiObject> set;
}
